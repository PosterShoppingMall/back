package adultdinosaurdooley.threesixnine.user.dto;

import lombok.*;
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
    private String userImg;
}
