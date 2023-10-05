package adultdinosaurdooley.threesixnine.product.entity;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @JoinColumn(name="product_name")
    private String name;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    @JoinColumn(name="category")
    private String category;

    @JoinColumn(name="product_size")
    private String size;

    @JoinColumn(name="product_contents")
    private String contents;

    @JoinColumn(name="product_price")
    private Integer price;

    @JoinColumn(name="sale_status")
    private String saleStatus;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }

    public int getStock() {
        return this.stock.getStockAmount();
    }

    public void setStock(int stockAmount) {
        this.stock.setStockAmount(stockAmount);
    }
}