package adultdinosaurdooley.threesixnine.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_img")
@Getter
@Setter
public class ProductImage {
    @Id
    @Column(name = "product_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String imagePath; // image 경로

//    @Lob
//    @Column // 원하는 길이로 설정
//    private List<String> images; // 썸네일



//    private String image2;
//
//    private String image3;
//
//    private String image4;
//
//    private String image5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
