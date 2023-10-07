package adultdinosaurdooley.threesixnine.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_img")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
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
    private Product product;

    public ProductImage(String imagePath, Product product){
        this.imagePath = imagePath;
        this.product = product;
    }

}