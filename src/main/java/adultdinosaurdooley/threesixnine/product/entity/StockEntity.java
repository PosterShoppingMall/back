package adultdinosaurdooley.threesixnine.product.entity;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity {

    @Id
    @Column(name = "stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //일대일 매핑 -> 즉시로딩을 기본 Fetch전략으로 설정
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "stock_amount", nullable = false)
    private int stockAmount; //상품 재고

    @Column(name = "sell_amount", nullable = false)
    private int sellAmount; //판매 수량

    public void removeStock(int stockAmount){
        int restStock = this.stockAmount - stockAmount;
        if(restStock < 0) {
            throw new OrderException(OrderErrorCode.OUT_OF_STOCK);
        }
        this.stockAmount = restStock;
    }

    public void addSellAmount(int sellAmount){
        int existedSellAmount = this.sellAmount + sellAmount;
        this.sellAmount = existedSellAmount;
    }

}