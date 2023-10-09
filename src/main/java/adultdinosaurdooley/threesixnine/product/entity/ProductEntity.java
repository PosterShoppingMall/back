package adultdinosaurdooley.threesixnine.product.entity;

import adultdinosaurdooley.threesixnine.product.entity.constant.ProductSaleStatus;
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
public class ProductEntity extends BaseTimeEntity{

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품코드

    @Column(name = "product_name",nullable = false , length=100)
    private String productName; //상품이름

    @Column(name = "product_price",nullable = false)
    private int productPrice; //상품가격

    @Column(nullable = false)
    private String category; //상품 카테고리

    @Column(name = "product_size",nullable = false)
    private String productSize; //상품 사이즈


    @Column(name = "product_contents",nullable = false)
    private String productContents; //상품 상세설명

    @Enumerated(EnumType.STRING)
    private ProductSaleStatus saleStatus; //상품 판매 상태


    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<ProductImageEntity> productImageEntity = new ArrayList<>();


    @OneToOne(mappedBy = "product", cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private StockEntity stock;
}