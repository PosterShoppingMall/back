package adultdinosaurdooley.threesixnine.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "stock")
@Getter
@Setter
@ToString
public class Stock {

    @Id
    @Column(name = "stock_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //일대일 매핑 -> 즉시로딩을 기본 Fetch전략으로 설정
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int stock_amount; //상품 재고

    private int sell_amount; //판매 수량
}
