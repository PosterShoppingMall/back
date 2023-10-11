package adultdinosaurdooley.threesixnine.cart.entity;

import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cartProduct")
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Column(name = "cart_cnt")
    private int cartCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public static CartProductEntity createCartProduct(CartEntity cartEntity, ProductEntity productEntity, int cartCnt) {
        CartProductEntity cartProductEntity = new CartProductEntity();
        cartProductEntity.setCartEntity(cartEntity);
        cartProductEntity.setProductEntity(productEntity);
        cartProductEntity.setCartCnt(cartCnt);
        return cartProductEntity;
    }

    // 이미 장바구니에 있는 상품 추가
    public void addCartProduct(int cartCnt){
        this.cartCnt += cartCnt;
    }

}
