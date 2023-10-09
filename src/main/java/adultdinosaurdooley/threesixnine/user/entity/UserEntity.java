package adultdinosaurdooley.threesixnine.user.entity;

import adultdinosaurdooley.threesixnine.cart.entity.CartEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true) // username 중목 안됨
    private String username;
    private String password;
    private String name;
    private String email;

//    @OneToOne(mappedBy = "userEntity")
//    private CartEntity cartEntity;


}
