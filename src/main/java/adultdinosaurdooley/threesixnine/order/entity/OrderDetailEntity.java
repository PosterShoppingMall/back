//package adultdinosaurdooley.threesixnine.order.entity;
//
//import adultdinosaurdooley.threesixnine.admin.entity.ProductEntity;
//import adultdinosaurdooley.threesixnine.admin.entity.Stock;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@Entity
//public class OrderDetailEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_detail_id")
//    private Long id;
//
//    @Column(name="ordered_amount", nullable = false)
//    private Integer orderedAmount;      // 주문 수량 == 장바구니 수량
//
//    @Column(name="ordered_price", nullable = false)
//    private Integer orderedPrice;       // 주문 가격 = 장바구니 개별가격
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private OrderEntity orderEntity;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Product_id")
//    private ProductEntity productEntity;
//
//
////    @OneToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "delivery_id")
////    private DeliveryInformation deliveryInformation;
//
//
//    public static OrderDetailEntity createOrderDetail(ProductEntity productEntity, int orderedAmount, Stock stock){
//        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
//        orderDetailEntity.setProductEntity(productEntity);      // 주문 상품
//        orderDetailEntity.setOrderedAmount(orderedAmount);      // 주문 수량
//        orderDetailEntity.setOrderedPrice(productEntity.getProductPrice());
//
//        stock.removeStock(orderedAmount);   // 주문수량 만큼 재고 슈량 감소
//        return orderDetailEntity;
//    }
//}
