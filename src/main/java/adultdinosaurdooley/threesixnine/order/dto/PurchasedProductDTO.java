package adultdinosaurdooley.threesixnine.order.dto;

import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasedProductDTO {

    private Long orderId;
    private LocalDateTime orderedDate;
    private Integer totalOrderPrice;
    private List<ResponseOrderProduct> orderedProducts;

    public static PurchasedProductDTO from(OrderEntity orderEntity, List<ResponseOrderProduct> orderedProducts) {
        // 주문된 상품들의 totalOrderedPrice 합계 계산
        Integer totalOrderedPrice = orderedProducts.stream()
                .mapToInt(ResponseOrderProduct::getTotalOrderedPrice)
                .sum();

        return PurchasedProductDTO.builder()
                .orderId(orderEntity.getId())
                .orderedDate(orderEntity.getOrderedAt())
                .totalOrderPrice(totalOrderedPrice) // totalOrderedPrice 합계 할당
                .orderedProducts(orderedProducts)
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ResponseOrderProduct{
        private Long productId;
        private String productName;
        private Integer orderedAmount;
        private Integer orderedPrice;
        private Integer totalOrderedPrice;
        private String orderedSize;
        private String orderedImagePath;
    }
}
