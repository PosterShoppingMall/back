package adultdinosaurdooley.threesixnine.users.dto;

import adultdinosaurdooley.threesixnine.users.entity.User;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MyPage {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String userImg;

    public static MyPage fromEntity(User user){
        return MyPage.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .postcode(user.getPostcode())
                .roadAddress(user.getRoadAddress())
                .detailAddress(user.getDetailAddress())
                .userImg(user.getUserImg())
                .build();
    }
}