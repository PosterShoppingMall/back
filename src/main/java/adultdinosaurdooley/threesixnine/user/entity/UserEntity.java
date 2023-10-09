package adultdinosaurdooley.threesixnine.user.entity;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.user.dto.UpdateMyPageDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity{

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

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "role")
    private String role;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "stored_name")
    private String storedName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;


    public static void update(UserEntity user, UpdateMyPageDTO updateMyPage){
        user.setPassword(updateMyPage.getPassword());
        user.setName(updateMyPage.getName());
        user.setPhoneNumber(updateMyPage.getPhoneNumber());
        user.setRoadAddress(updateMyPage.getRoadAddress());
        user.setDetailAddress(updateMyPage.getDetailAddress());
        user.setUserImg(String.valueOf(updateMyPage.getUserImg()));
        user.setPostCode(updateMyPage.getPostCode());
    }
}
