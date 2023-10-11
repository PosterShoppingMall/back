package adultdinosaurdooley.threesixnine.order.dto;

import adultdinosaurdooley.threesixnine.cart.dto.CartOrderDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderRequestDTO {

    private CartOrderDTO cartOrderDTO;
    private OrderAddressDTO orderAddressDTO;

}