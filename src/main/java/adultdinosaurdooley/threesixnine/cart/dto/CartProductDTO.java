package adultdinosaurdooley.threesixnine.cart.dto;

import adultdinosaurdooley.threesixnine.cart.entity.CartProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {

    private Long cartProductId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요.")
    private int cartCnt;


    public static CartProductDTO toCartProductDTO(CartProductEntity cartProductEntity){
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setCartProductId(cartProductEntity.getId());
        cartProductDTO.setCartCnt(cartProductEntity.getCartCnt());

        return cartProductDTO;
    }
}
