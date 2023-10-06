package adultdinosaurdooley.threesixnine.cart.dto;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCartDto {

    private Long cartId;
    private List<CartProduct> cartProducts;

    public static GetCartDto from(Cart cart, List<CartProduct> cartProducts) {
        return GetCartDto.builder()
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

}

