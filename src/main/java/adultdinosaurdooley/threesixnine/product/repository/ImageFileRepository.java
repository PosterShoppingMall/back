package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ProductImage, Long> {


}
