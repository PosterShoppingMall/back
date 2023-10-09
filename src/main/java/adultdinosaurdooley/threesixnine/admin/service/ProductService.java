package adultdinosaurdooley.threesixnine.admin.service;

import adultdinosaurdooley.threesixnine.admin.dto.ProductDTO;
import adultdinosaurdooley.threesixnine.admin.dto.StockDTO;
import adultdinosaurdooley.threesixnine.admin.dto.UpdateProductDTO;
import adultdinosaurdooley.threesixnine.admin.entity.Product;

import adultdinosaurdooley.threesixnine.admin.entity.ProductImage;
import adultdinosaurdooley.threesixnine.admin.entity.Stock;
import adultdinosaurdooley.threesixnine.admin.repository.ImageFileRepository;
import adultdinosaurdooley.threesixnine.admin.repository.ProductRepository;
import adultdinosaurdooley.threesixnine.admin.repository.StockRepository;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final StockRepository stockRepository;
    private final ImageFileRepository imageFileRepository;

    //상품 등록
    public ResponseEntity<Map<String, String>> resisterProducts(List<MultipartFile> multipartFilelist, ProductDTO productDTO) throws IOException {

        // DTO -> Entity로 변환
        Product product = Product.builder()
                                 .productName(productDTO.getProductName())
                                 .productPrice(productDTO.getProductPrice())
                                 .category(productDTO.getCategory())
                                 .productContents(productDTO.getProductContents())
                                 .productSize(productDTO.getProductSize())
                                 .saleStatus(productDTO.getSaleStatus())
                                 .build();
        //상품 db 저장
        Long id = productRepository.save(product)
                                   .getId();

        // stock dto -> entity 로 변환
        Stock stock = Stock.builder()
                           .product(product)
                           .stockAmount(productDTO.getStockDTO().getStockAmount())
                           .sellAmount(productDTO.getStockDTO().getSellAmount())
                           .build();

        //재고 db 저장
        stockRepository.save(stock);

        //이미지 db 저장
        if (multipartFilelist != null) {
            s3Service.upload(multipartFilelist, "static", product);
        }

        //상품이 제대로 등록 되었는 지 확인
        Optional<Product> findId = productRepository.findById(id);

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
        Optional<Product> productOptional = productRepository.findById(productId);

        System.out.println("productOptional = " + productOptional);

        //1. 상품 수정
        if (productOptional.isPresent()) {
            //product 엔티티 값 가져오기
            Product product = productOptional.get();

            //dto-> entity 변환
            product.setProductName(updateProductDTO.getProductName());
            product.setProductPrice(updateProductDTO.getProductPrice());
            product.setProductSize(updateProductDTO.getProductSize());
            product.setProductContents(updateProductDTO.getProductContents());
            product.getStock().setStockAmount(updateProductDTO.getStockAmount());
            product.setCategory(updateProductDTO.getCategory());
            product.setSaleStatus(updateProductDTO.getSaleStatus());


        //2. 상품 이미지 수정
            if (multipartFilelist != null) {
                List<ProductImage> productImages = imageFileRepository.findAllByProduct(product);
                //List<ProductImage> productImages = imageFileRepository.findAllById(productId);
                System.out.println("productImages = " + productImages);
                //기존 이미지 불러오기
                for (ProductImage productImage : productImages) {

                    String imagePath = productImage.getImagePath();
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
                imageFileRepository.deleteAllByProduct(product);

                //s3와 디비에 새로운 이미지 추가
                s3Service.upload(multipartFilelist, "static", product);

                //상품 정보 수정
                productRepository.save(product);

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
        List<Product> producList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : producList) {
            ProductDTO productDTO = ProductDTO.builder()
                                              .id(product.getId())
                                              .productName(product.getProductName())
                                              .productPrice(product.getProductPrice())
                                              .category(product.getCategory())
                                              .productContents(product.getProductContents())
                                              .productSize(product.getProductSize())
                                              .saleStatus(product.getSaleStatus())
                                              .createdAt(product.getCreatedAt())
                                              .updatedAt(product.getUpdatedAt())
                                              .stockDTO(StockDTO.builder()
                                                                .stockAmount(product.getStock().getStockAmount())
                                                                .sellAmount(product.getStock().getSellAmount())
                                                                .build())
                                              .build();

            //상품 이미지 전체 조회
            List<String> productImages = new ArrayList<>();
            for (ProductImage productImage : product.getProductImages()) {
                Map<String, Object> imageInfo = new HashMap<>();

                imageInfo.put("imageNum", productImage.getImageNum());
                imageInfo.put("imagePath", productImage.getImagePath());

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
        List<Product> producList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : producList) {
            ProductDTO productDTO = ProductDTO.builder()
                                              .id(product.getId())
                                              .productName(product.getProductName())
                                              .productPrice(product.getProductPrice())
                                              .category(product.getCategory())
                                              .productContents(product.getProductContents())
                                              .productSize(product.getProductSize())
                                              .saleStatus(product.getSaleStatus())
                                              .createdAt(product.getCreatedAt())
                                              .updatedAt(product.getUpdatedAt())
                                              .stockDTO(StockDTO.builder()
                                                                .stockAmount(product.getStock().getStockAmount())
                                                                .sellAmount(product.getStock().getSellAmount())
                                                                .build())
                                              .build();

            //1번째 사진만 조회(썸네일사진)
            List<String> productImages = new ArrayList<>();
            for (ProductImage productImage : product.getProductImages()) {
                if (productImage.getImageNum() == 1) {
                    Map<String, Object> imageInfo = new HashMap<>();
                    imageInfo.put("imageNum", productImage.getImageNum());
                    imageInfo.put("imagePath", productImage.getImagePath());
                    productImages.add(String.valueOf(imageInfo));
                }
            }
            productDTOList.add(new ProductDTO(productDTO, productImages));
        }
        return ResponseEntity.status(200)
                             .body(productDTOList);
    }

}
