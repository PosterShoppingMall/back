package adultdinosaurdooley.threesixnine.product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductImageDTO {

    private String imagePath;

    private int imageNum;
}