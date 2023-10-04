package adultdinosaurdooley.threesixnine.users.entity;

import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPage;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "User_Details")
@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "address_detail")
    private String addressDetail;

    public static void update(UserDetail userDetail, UpdateMyPage updateMyPage) {
        userDetail.setUserName(updateMyPage.getUserName());
        userDetail.setPhoneNumber(updateMyPage.getPhoneNumber());
        userDetail.setAddress(updateMyPage.getAddress());
        userDetail.setAddressDetail(updateMyPage.getAddressDetail());
    }

}
