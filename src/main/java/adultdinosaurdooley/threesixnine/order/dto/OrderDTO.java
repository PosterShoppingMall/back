package adultdinosaurdooley.threesixnine.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    // cartOrderDto 를 받아서 주문 로직 으로 넘길 때 사용

    private Long productId;

    private int orderCount;
}
