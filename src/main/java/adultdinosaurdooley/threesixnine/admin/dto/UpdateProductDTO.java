package adultdinosaurdooley.threesixnine.admin.dto;

import adultdinosaurdooley.threesixnine.admin.entity.constant.ProductSaleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateProductDTO {

    private String productName;
    private int productPrice;
    private String category;
    private String productSize;
    private String productContents;
    private ProductSaleStatus saleStatus;
    private int stockAmount;
}
