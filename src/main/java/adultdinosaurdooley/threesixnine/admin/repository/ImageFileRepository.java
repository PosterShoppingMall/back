package adultdinosaurdooley.threesixnine.admin.repository;

import adultdinosaurdooley.threesixnine.admin.entity.Product;
import adultdinosaurdooley.threesixnine.admin.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageFileRepository extends JpaRepository<ProductImage, Long> {

  //  List<ProductImage> findAllByProduct(Product product);

    //List<ProductImage> findAllById(Long productId);

    @Modifying
    @Query("DELETE FROM ProductImage pi WHERE pi.product = ?1")
    void deleteAllByProduct(Product product);

    List<ProductImage> findAllByProduct(Product product);
}
