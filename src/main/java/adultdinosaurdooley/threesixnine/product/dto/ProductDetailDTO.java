package adultdinosaurdooley.threesixnine.product.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long productId;
    private String productName;
    private int productPrice;
    private String productSize;
    private String productContents;
    private List<ProductImageDto> productImages;
}
