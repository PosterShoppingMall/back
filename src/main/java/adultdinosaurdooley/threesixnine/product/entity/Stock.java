package adultdinosaurdooley.threesixnine.product.entity;

import adultdinosaurdooley.threesixnine.users.entity.User;
import lombok.*;

import javax.persistence.*;
@Getter
@Table(name = "stock")
@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "stock_amount", nullable = false)
    private Integer stockAmount;

    @Column(name = "sell_amount", nullable = false)
    private Integer sellAmount;


}
