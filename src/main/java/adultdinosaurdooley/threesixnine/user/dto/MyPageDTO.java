package adultdinosaurdooley.threesixnine.user.dto;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MyPageDTO {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String postCode;
    private String roadAddress;
    private String detailAddress;
    private String userImg;

    public static MyPageDTO fromEntity(UserEntity user){
        return MyPageDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .postCode(user.getPostCode())
                .roadAddress(user.getRoadAddress())
                .detailAddress(user.getDetailAddress())
                .userImg(user.getUserImg())
                .build();
    }
}