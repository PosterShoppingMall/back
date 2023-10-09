package adultdinosaurdooley.threesixnine.product.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name="product_name")
    private String name;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    @Column(name="category")
    private String category;

    @Column(name="product_size")
    private String size;

    @Column(name="product_contents")
    private String contents;

    @Column(name="product_price")
    private Integer price;

    @Column(name="sale_status")
    private String saleStatus;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

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
