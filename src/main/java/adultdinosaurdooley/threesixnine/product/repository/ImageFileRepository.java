package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ProductImageEntity, Long> {


}
