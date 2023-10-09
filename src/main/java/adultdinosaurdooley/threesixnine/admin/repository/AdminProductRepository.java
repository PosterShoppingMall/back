package adultdinosaurdooley.threesixnine.admin.repository;


import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AdminProductRepository extends JpaRepository<ProductEntity,Long>{

}
