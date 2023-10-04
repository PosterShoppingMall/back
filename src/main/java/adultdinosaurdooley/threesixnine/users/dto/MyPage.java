package adultdinosaurdooley.threesixnine.users.dto;

import adultdinosaurdooley.threesixnine.users.entity.UserDetail;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MyPage {

    private String userName;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;

    public static MyPage fromEntity(UserDetail userDetail){
        return MyPage.builder()
                .userName(userDetail.getUserName())
                .email(userDetail.getUser().getEmail())
                .phoneNumber(userDetail.getPhoneNumber())
                .address(userDetail.getAddress())
                .addressDetail(userDetail.getAddressDetail())
                .build();
    }
}