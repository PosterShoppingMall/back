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
    private Integer totalCartProductPrice;
    private List<CartProduct> cartProducts;

    public static GetCartDto from(Cart cart, List<CartProduct> cartProducts) {

        int totalPrice = cartProducts.stream()
                .mapToInt(CartProduct::getCartProductAmount)
                .sum();

        return GetCartDto.builder()
                .cartId(cart.getId())
                .totalCartProductPrice(totalPrice)
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
        private Integer cartProductAmount;
        private Integer cartProductAmountPrice;
        private String productImagePath;
    }
}

