package adultdinosaurdooley.threesixnine.cart.entity;

//import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL)
    private List<CartProductEntity> cartProductEntities = new ArrayList<>();

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity userEntity;

    // 처음 장바구니를 담을 때
//    public static CartEntity createCart(UserEntity userEntity) {
//        CartEntity cartEntity = new CartEntity();
//        cartEntity.setUserEntity(userEntity);
//        return cartEntity;
//    }




}
