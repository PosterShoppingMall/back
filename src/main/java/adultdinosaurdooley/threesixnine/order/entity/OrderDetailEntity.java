package adultdinosaurdooley.threesixnine.order.entity;


import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order_detail")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(name="ordered_amount", nullable = false)
    private Integer orderedAmount;      // 주문 수량 == 장바구니 수량

    @Column(name="ordered_price", nullable = false)
    private Integer orderedPrice;       // 주문 가격 = 장바구니 개별가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_id")
    private ProductEntity productEntity;


    public static OrderDetailEntity createOrderDetail(ProductEntity productEntity, int orderedAmount){
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setProductEntity(productEntity);      // 주문 상품
        orderDetailEntity.setOrderedAmount(orderedAmount);      // 주문 수량
        orderDetailEntity.setOrderedPrice(productEntity.getProductPrice());

        productEntity.getStockEntity().removeStock(orderedAmount);   // 주문수량 만큼 재고 수량 감소
        productEntity.getStockEntity().addSellAmount(orderedAmount);
        return orderDetailEntity;
    }
}
