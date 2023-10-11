package adultdinosaurdooley.threesixnine.product.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListDTO {
    private Long productId;
    private String productName;
    private int productPrice;
    private String imageUrl;
    private int stockAmount;
}
