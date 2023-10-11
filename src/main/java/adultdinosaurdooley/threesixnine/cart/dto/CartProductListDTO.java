package adultdinosaurdooley.threesixnine.cart.dto;

import adultdinosaurdooley.threesixnine.cart.entity.CartEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductListDTO {

    private Long cartId;
    private Integer totalPriceOfCart;
    private List<CartProduct> cartProductList;


    public static CartProductListDTO from(CartEntity cartEntity, List<CartProduct> cartProductList) {

        int totalPrice = cartProductList.stream()
                .mapToInt(CartProduct::getCartProductTotalPrice)
                .sum();

        return CartProductListDTO.builder()
                .cartId(cartEntity.getId())
                .totalPriceOfCart(totalPrice)
                .cartProductList(cartProductList)
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
        private Integer cartProductTotalPrice;
        private String imageUrl;
    }


}
