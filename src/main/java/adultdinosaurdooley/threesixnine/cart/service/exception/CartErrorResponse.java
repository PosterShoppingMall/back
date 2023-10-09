package adultdinosaurdooley.threesixnine.cart.service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartErrorResponse {
    private CartErrorCode errorCode;
    private String errorMessage;
}
