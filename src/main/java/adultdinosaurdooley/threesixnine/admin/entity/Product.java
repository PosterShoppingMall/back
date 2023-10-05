package adultdinosaurdooley.threesixnine.admin.entity;

import adultdinosaurdooley.threesixnine.admin.entity.constant.ProductSaleStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseTimeEntity{
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품코드

    @Column(nullable = false , length=100)
    private String product_name; //상품이름

    @Column(nullable = false)
    private int product_price; //상품가격

    @Column(nullable = false)
    private String category; //상품 카테고리

    @Column(nullable = false)
    private String product_size; //상품 사이즈

    @Lob
    @Column(nullable = false)
    private String product_contents; //상품 상세설명

    @Enumerated(EnumType.STRING)
    private ProductSaleStatus sale_status; //상품 판매 상태

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProductImage> productImages = new ArrayList<>();



    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Stock stock;
}
