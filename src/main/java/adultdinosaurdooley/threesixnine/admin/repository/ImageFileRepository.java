package adultdinosaurdooley.threesixnine.admin.repository;


import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageFileRepository extends JpaRepository<ProductImageEntity, Long> {

    List<ProductImageEntity> findAllByProduct(ProductEntity product);

    //List<ProductImage> findAllById(Long productId);


    @Modifying
    @Query("DELETE FROM ProductImageEntity pi WHERE pi.product = ?1")
    void deleteAllByProduct(ProductEntity product);


//    @Query("SELECT pi.imagePath FROM ProductImageEntity  pi WHERE pi.productEntity =: product AND pi.imageNum = 1")
//    List<String> findImagePathsByProductAndImageNumIsOne(@Param("product") ProductEntity productEntity);


}
