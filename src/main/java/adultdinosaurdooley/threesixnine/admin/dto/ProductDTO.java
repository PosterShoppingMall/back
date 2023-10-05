package adultdinosaurdooley.threesixnine.admin.dto;

import adultdinosaurdooley.threesixnine.admin.entity.constant.ProductSaleStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long id;
    private String productName;
    private int productPrice;
    private String category;
    private String productSize;
    private String productContents;
    private ProductSaleStatus saleStatus;
    private List<ProductImageDto> productImages;
    private StockDTO stockDTO;

    @Builder
    public ProductDTO(
            Long id, String productName, String productSize, int productPrice, String category, String productContents, ProductSaleStatus saleStatus,
            List<ProductImageDto> productImages, StockDTO stockDTO) {
        this.id = id;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.category = category;
        this.productContents = productContents;
        this.saleStatus = saleStatus;
        this.productImages = productImages;
        this.stockDTO = stockDTO;
    }


}
