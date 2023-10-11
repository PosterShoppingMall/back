package adultdinosaurdooley.threesixnine.order.entity;

import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="orderDetail")
@Entity
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orders;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryInformation deliveryInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_id")
    private ProductEntity product;

    @Column(name="ordered_amount", nullable = false)
    private Integer orderedAmount;

    @Column(name="ordered_price", nullable = false)
    private Integer orderedPrice;


}
