package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
