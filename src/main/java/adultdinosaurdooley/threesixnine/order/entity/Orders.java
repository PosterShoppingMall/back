package adultdinosaurdooley.threesixnine.order.entity;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryInformation deliveryInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreatedDate
    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name="total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name="order_status", nullable = false)
    private String orderStatus;
}
