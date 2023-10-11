package adultdinosaurdooley.threesixnine.admin.service;

import adultdinosaurdooley.threesixnine.admin.dto.ProductDTO;
import adultdinosaurdooley.threesixnine.admin.dto.StockDTO;
import adultdinosaurdooley.threesixnine.admin.dto.UpdateProductDTO;

import adultdinosaurdooley.threesixnine.admin.repository.ImageFileRepository;
import adultdinosaurdooley.threesixnine.admin.repository.AdminProductRepository;
import adultdinosaurdooley.threesixnine.admin.repository.StockRepository;
import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.entity.ProductImageEntity;
import adultdinosaurdooley.threesixnine.product.entity.StockEntity;
import adultdinosaurdooley.threesixnine.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminProductRepository adminProductRepository;
    private final S3Service s3Service;
    private final StockRepository stockRepository;
    private final ImageFileRepository imageFileRepository;

    //상품 등록
    public ResponseEntity<Map<String, String>> resisterProducts(List<MultipartFile> multipartFilelist, ProductDTO productDTO) throws IOException {

        // DTO -> Entity로 변환
        ProductEntity productEntity = ProductEntity.builder()
                                             .productName(productDTO.getProductName())
                                             .productPrice(productDTO.getProductPrice())
                                             .category(productDTO.getCategory())
                                             .productContents(productDTO.getProductContents())
                                             .productSize(productDTO.getProductSize())
                                             .saleStatus(productDTO.getSaleStatus())
                                             .build();
        //상품 db 저장
        Long id = adminProductRepository.save(productEntity)
                                        .getId();

        // stock dto -> entity 로 변환
        StockEntity stockEntity = StockEntity.builder()
                                             .productEntity(productEntity)
                                             .stockAmount(productDTO.getStockDTO().getStockAmount())
                                             .sellAmount(productDTO.getStockDTO().getSellAmount())
                                             .build();

        //재고 db 저장
        stockRepository.save(stockEntity);

        //이미지 db 저장
        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", productEntity);
        }

        //상품이 제대로 등록 되었는 지 확인
        Optional<ProductEntity> findId = adminProductRepository.findById(id);

        Map<String, String> map = new HashMap<>();

        if (findId.isPresent()) {
            map.put("message", "상품이 성공적으로 등록되었습니다.");
        } else {
            map.put("message", "상품 등록이 실패하였습니다.");
        }
        return ResponseEntity.status(200)
                             .body(map);
    }

    //상품 수정
    @Transactional
    public ResponseEntity<Map<String, String>> updateProduct(List<MultipartFile> multipartFilelist, Long productId, UpdateProductDTO updateProductDTO) throws IOException {
        Map<String, String> map = new HashMap<>();

        //수정할 상품 productId에 해당하는 상품
        Optional<ProductEntity> productOptional = adminProductRepository.findById(productId);

        System.out.println("productOptional = " + productOptional);

        //1. 상품 수정
        if (productOptional.isPresent()) {
            //product 엔티티 값 가져오기
            ProductEntity productEntity = productOptional.get();

            //dto-> entity 변환
            productEntity.setProductName(updateProductDTO.getProductName());
            productEntity.setProductPrice(updateProductDTO.getProductPrice());
            productEntity.setProductSize(updateProductDTO.getProductSize());
            productEntity.setProductContents(updateProductDTO.getProductContents());
            productEntity.getStockEntity().setStockAmount(updateProductDTO.getStockAmount());
            productEntity.setCategory(updateProductDTO.getCategory());
            productEntity.setSaleStatus(updateProductDTO.getSaleStatus());





        //2. 상품 이미지 수정
            if (multipartFilelist != null) {
                List<ProductImageEntity> productImageEntities = imageFileRepository.findAllByProductEntity(productEntity);
                //List<ProductImage> productImages = imageFileRepository.findAllById(productId);
                System.out.println("productImages = " + productImageEntities);
                //기존 이미지 불러오기
                for (ProductImageEntity productImageEntity : productImageEntities) {

                    String imagePath = productImageEntity.getImagePath();
                    System.out.println("imagePath = " + imagePath);

                    //s3 에 삭제할 url 전달


                    //static/ 포함한 url
                    int startIndex = imagePath.indexOf("static/");
                    String filename = imagePath.substring(startIndex);
                    System.out.println("filename = " + filename);

                    //s3 이미지 삭제
                    s3Service.deleteFile(filename);
                }

                // DB 삭제
                imageFileRepository.deleteAllByProduct(productEntity);

                //s3와 디비에 새로운 이미지 추가
                s3Service.upload(multipartFilelist, "static", productEntity);

                //상품 정보 수정
                adminProductRepository.save(productEntity);

                map.put("message", "상품 수정이 완료되었습니다.");
            } else {
                map.put("message", "해당 이미지를 찾을 수 없거나 업로드할 새 이미지가 없습니다.");
            }
        } else {
            map.put("message", "상품을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(map);
    }


    //상품 전체 조회 (이미지 전체 조회)
    public ResponseEntity<List<ProductDTO>> findAll() {
        //entity -> dto
        List<ProductEntity> producList = adminProductRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductEntity productEntity : producList) {
            ProductDTO productDTO = ProductDTO.builder()
                                              .id(productEntity.getId())
                                              .productName(productEntity.getProductName())
                                              .productPrice(productEntity.getProductPrice())
                                              .category(productEntity.getCategory())
                                              .productContents(productEntity.getProductContents())
                                              .productSize(productEntity.getProductSize())
                                              .saleStatus(productEntity.getSaleStatus())
                                              .createdAt(productEntity.getCreatedAt())
                                              .updatedAt(productEntity.getUpdatedAt())
                                              .stockDTO(StockDTO.builder()
                                                                .stockAmount(productEntity.getStockEntity().getStockAmount())
                                                                .sellAmount(productEntity.getStockEntity().getSellAmount())
                                                                .build())
                                              .build();

            //상품 이미지 전체 조회
            List<String> productImages = new ArrayList<>();
            for (ProductImageEntity productImageEntity : productEntity.getProductImageEntity()) {
                Map<String, Object> imageInfo = new HashMap<>();

                imageInfo.put("imageNum", productImageEntity.getImageNum());
                imageInfo.put("imagePath", productImageEntity.getImagePath());

                productImages.add(String.valueOf(imageInfo));
                //productImages.add(productImage.getImagePath());
            }
            productDTOList.add(new ProductDTO(productDTO, productImages));
        }
        return ResponseEntity.status(200)
                             .body(productDTOList);
    }


    //썸네일 이미지 1개만 포함해서 전체 상품 리스트 조회
    public ResponseEntity<List<ProductDTO>> findAllByThumbnail() {


        //entity -> dto
        List<ProductEntity> producList = adminProductRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductEntity productEntity : producList) {
            ProductDTO productDTO = ProductDTO.builder()
                                              .id(productEntity.getId())
                                              .productName(productEntity.getProductName())
                                              .productPrice(productEntity.getProductPrice())
                                              .category(productEntity.getCategory())
                                              .productContents(productEntity.getProductContents())
                                              .productSize(productEntity.getProductSize())
                                              .saleStatus(productEntity.getSaleStatus())
                                              .createdAt(productEntity.getCreatedAt())
                                              .updatedAt(productEntity.getUpdatedAt())
                                              .stockDTO(StockDTO.builder()
                                                                .stockAmount(productEntity.getStockEntity().getStockAmount())
                                                                .sellAmount(productEntity.getStockEntity().getSellAmount())
                                                                .build())

                                              .build();

            //1번째 사진만 조회(썸네일사진)
            List<String> productImages = new ArrayList<>();
            for (ProductImageEntity productImageEntity : productEntity.getProductImageEntity()) {
                if (productImageEntity.getImageNum() == 1) {
                    Map<String, Object> imageInfo = new HashMap<>();
                    imageInfo.put("imageNum", productImageEntity.getImageNum());
                    imageInfo.put("imagePath", productImageEntity.getImagePath());
                    productImages.add(String.valueOf(imageInfo));
                }
            }




            //List<String> imagePaths = imageFileRepository.findImagePathsByProductAndImageNumIsOne(product);

             productDTOList.add(new ProductDTO(productDTO, productImages));
            // productDTOList.add(new ProductDTO(productDTO, imagePaths));
        }
        return ResponseEntity.status(200)
                             .body(productDTOList);
    }

}
