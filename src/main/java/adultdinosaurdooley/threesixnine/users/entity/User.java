package adultdinosaurdooley.threesixnine.users.entity;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPage;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "user_img", nullable = false)
    private String userImg;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;


    public static void update(User user, UpdateMyPage updateMyPage){
        user.setPassword(updateMyPage.getPassword());
        user.setName(updateMyPage.getName());
        user.setPhoneNumber(updateMyPage.getPhoneNumber());
        user.setRoadAddress(updateMyPage.getRoadAddress());
        user.setDetailAddress(updateMyPage.getDetailAddress());
        user.setUserImg(updateMyPage.getUserImg());
        user.setPostcode(updateMyPage.getPostcode());
    }

}
