package adultdinosaurdooley.threesixnine.product.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_img")
@Getter
@Setter
@NoArgsConstructor
public class ProductImageEntity {
    @Id
    @Column(name = "product_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String imagePath; // image 경로

    @Column(nullable = false)
    private int imageNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public ProductImageEntity(String imagePath, ProductEntity productEntity) {
        this.imagePath = imagePath;
        this.productEntity = productEntity;
    }
}