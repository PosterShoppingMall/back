package adultdinosaurdooley.threesixnine.cart.dto;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartCheck {

    private Long cartId;
    private List<CartProduct> cartProducts;

    public static CartCheck from(Cart cart, List<CartProduct> cartProducts) {
        return CartCheck.builder()
                .cartId(cart.getId())
                .cartProducts(cartProducts)
                .build();
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CartProduct{
        private Long productId;
        private String productName;
        private Integer productPrice;
        private Integer cartCnt;
        private Integer cartProductAmount;
    }

    private static DecimalFormat formatter = new DecimalFormat("###,###");
}

