package adultdinosaurdooley.threesixnine.admin.service;

import adultdinosaurdooley.threesixnine.admin.dto.ProductDTO;
import adultdinosaurdooley.threesixnine.admin.dto.StockDTO;
import adultdinosaurdooley.threesixnine.admin.entity.Product;

import adultdinosaurdooley.threesixnine.admin.entity.ProductImage;
import adultdinosaurdooley.threesixnine.admin.entity.Stock;
import adultdinosaurdooley.threesixnine.admin.repository.ProductRepository;
import adultdinosaurdooley.threesixnine.admin.repository.StockRepository;
import adultdinosaurdooley.threesixnine.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final StockRepository stockRepository;

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

    /* 이미지 포함 전체조회 (stock -> null 로 처리됨 )


    //상품 전체 조회
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
                                              .build();



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

*/

}
