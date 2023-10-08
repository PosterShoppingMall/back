package adultdinosaurdooley.threesixnine.user.entity;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.user.dto.UpdateMyPageDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "role")
    private String role;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "stored_name")
    private String storedName;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;


    public static void update(UserEntity user, UpdateMyPageDTO updateMyPage){
        user.setPassword(updateMyPage.getPassword());
        user.setName(updateMyPage.getName());
        user.setPhoneNumber(updateMyPage.getPhoneNumber());
        user.setRoadAddress(updateMyPage.getRoadAddress());
        user.setDetailAddress(updateMyPage.getDetailAddress());
        user.setUserImg(updateMyPage.getUserImg());
        user.setPostCode(updateMyPage.getPostCode());
    }

}
