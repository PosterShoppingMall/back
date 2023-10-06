package adultdinosaurdooley.threesixnine.admin.repository;

import adultdinosaurdooley.threesixnine.admin.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ProductImage, Long> {
}
