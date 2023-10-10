package adultdinosaurdooley.threesixnine.order.dto;

import adultdinosaurdooley.threesixnine.order.entity.Orders;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellProductDto {

    private Long orderId;
    private String orderStatus;
    private LocalDateTime orderedDate;
    private Integer totalOrderPrice;
    private List<ResponseOrderProduct> orderedProducts;

    public static SellProductDto from(Orders orders, List<ResponseOrderProduct> orderedProducts) {
        return SellProductDto.builder()
                .orderId(orders.getId())
                .orderStatus(orders.getOrderStatus())
                .orderedDate(orders.getOrderedAt())
                .totalOrderPrice(orders.getTotalAmount())
                .orderedProducts(orderedProducts)
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseOrderProduct{
        private Long orderDetailId;
        private Long productId;
        private String productName;
        private Integer orderedAmount;
        private Integer orderedPrice;
        private Integer totalOrderedPrice;
        private String orderedSize;
        private String orderedImagePath;
    }

}

