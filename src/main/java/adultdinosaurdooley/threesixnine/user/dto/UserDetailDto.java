package adultdinosaurdooley.threesixnine.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {

    private String email;
    private String name;
    private String phoneNumber;
    private String postCode;
    private String roadAddress;
    private String detailAddress;
    private String userImgUrl;

}
