package adultdinosaurdooley.threesixnine.order.entity;

import adultdinosaurdooley.threesixnine.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryInformation deliveryInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_id")
    private Product product;

    @Column(name="ordered_amount", nullable = false)
    private Integer orderedAmount;

    @Column(name="ordered_price", nullable = false)
    private Integer orderedPrice;


}
