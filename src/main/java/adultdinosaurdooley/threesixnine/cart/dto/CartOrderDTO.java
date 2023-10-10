package adultdinosaurdooley.threesixnine.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartOrderDTO {

    // 장바 구니 주문할 때 요청값 으로 사용

    //장바구니 고유 식별자
    private List<Long> cartProductIdList;


}
