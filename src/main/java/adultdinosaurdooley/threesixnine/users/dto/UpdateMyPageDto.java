package adultdinosaurdooley.threesixnine.users.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMyPageDto {

    private String name;
    private String password;
    private String phoneNumber;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String userImg;
}
