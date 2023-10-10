package adultdinosaurdooley.threesixnine.order.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DeliveryInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(name="delivery_name", nullable = false)
    private String deliveryName;

    @Column(name="delivery_phone", nullable = false)
    private String deliveryPhone;

    @Column(name="delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name="delivery_address_detail", nullable = false)
    private String deliveryAddressDetail;

    @Column(name="delivery_postcode", nullable = false)
    private String deliveryPostCode;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

}
