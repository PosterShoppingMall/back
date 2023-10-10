package adultdinosaurdooley.threesixnine.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    // 장바 구니 에 상품을 담을 때 사용 하는 DTO

    @NotNull
    private Long productId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요.")
    private int cartCnt;

}
