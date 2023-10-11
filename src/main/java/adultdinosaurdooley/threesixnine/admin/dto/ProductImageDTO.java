package adultdinosaurdooley.threesixnine.admin.dto;

import lombok.*;

import java.util.List;

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
