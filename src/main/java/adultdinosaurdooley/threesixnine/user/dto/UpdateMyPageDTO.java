package adultdinosaurdooley.threesixnine.user.dto;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMyPageDTO {

    private String name;
    private String password;
    private String phoneNumber;
    private String postCode;
    private String roadAddress;
    private String detailAddress;
    private MultipartFile userImg;
    private String email;



}
