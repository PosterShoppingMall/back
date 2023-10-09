package adultdinosaurdooley.threesixnine.product.dto;

import adultdinosaurdooley.threesixnine.product.entity.constant.ProductSaleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    private Long id;
    private String productName;
    private int productPrice;
    private String category;
    private String productSize;
    private String productContents;
    private ProductSaleStatus saleStatus;
    private List<String> productImages;
    private StockDTO stockDTO;

    //등록시간 format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    @Builder
    public ProductDto(
            Long id, String productName, String productSize, int productPrice, String category, String productContents, ProductSaleStatus saleStatus,
            List<String> productImages, StockDTO stockDTO , LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.category = category;
        this.productContents = productContents;
        this.saleStatus = saleStatus;
        this.productImages = productImages;
        this.stockDTO = stockDTO;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    @Builder
    public ProductDto(ProductDto productDTO, List<String> productImages) {
        this.id = productDTO.getId();
        this.productName = productDTO.getProductName();
        this.productSize = productDTO.getProductSize();
        this.productPrice = productDTO.getProductPrice();
        this.category = productDTO.getCategory();
        this.productContents = productDTO.getProductContents();
        this.saleStatus = productDTO.getSaleStatus();
        this.productImages = productImages;
        this.stockDTO = productDTO.getStockDTO();
        this.createdAt =productDTO.getCreatedAt();
        this.updatedAt = productDTO.getUpdatedAt();
    }
}