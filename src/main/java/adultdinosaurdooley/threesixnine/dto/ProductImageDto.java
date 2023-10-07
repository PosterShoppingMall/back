package adultdinosaurdooley.threesixnine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductImageDto {

    private String imagePath;

    private int imageNum;
}