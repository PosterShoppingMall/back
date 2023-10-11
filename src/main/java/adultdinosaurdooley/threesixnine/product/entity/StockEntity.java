package adultdinosaurdooley.threesixnine.product.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Table(name = "stock")
@Entity
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;

    @Column(name = "stock_amount", nullable = false)
    private Integer stockAmount;

    @Column(name = "sell_amount", nullable = false)
    private Integer sellAmount;

    public StockEntity(ProductEntity product, int stockAmount, int sellAmount){
        this.product =product;
        this.stockAmount =stockAmount;
        this.sellAmount = sellAmount;
    }


}
