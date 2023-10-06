package adultdinosaurdooley.threesixnine.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListDTO {
    private Long id;
    private String productName;
    private int productPrice;
//    private String imageUrl;
    private int stockAmount;
}
