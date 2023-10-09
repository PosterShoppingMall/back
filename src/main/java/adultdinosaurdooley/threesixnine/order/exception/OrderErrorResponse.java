package adultdinosaurdooley.threesixnine.order.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderErrorResponse {
    private OrderErrorCode errorCode;
    private String errorMessage;
}
