package adultdinosaurdooley.threesixnine.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long id;
    private String productName;
    private int productPrice;
    private String productSize;
    private String productContents;

    //private List<ProductImageDto> productImages;
}
