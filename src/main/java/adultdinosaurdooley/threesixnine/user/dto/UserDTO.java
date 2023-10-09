package adultdinosaurdooley.threesixnine.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String postCode; // 우편번호
    private String roadAddress; // 도로명주소
    private String detailAddress; // 상세주소
    private MultipartFile userImg;
    private String role;
    private LocalDateTime createdAt;

}
