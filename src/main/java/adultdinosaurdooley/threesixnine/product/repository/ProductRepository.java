package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // 메인페이지
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' ORDER BY p.createdAt DESC")
    List<ProductEntity> findTop6ByOrderByCreatedAt(Pageable pageable);
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' AND p.category = :category ORDER BY p.createdAt DESC")
    List<ProductEntity> findTop6ByCategoryOrderByCreatedAt(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' ORDER BY s.sellAmount DESC")
    List<ProductEntity> findTop6ByOrderBySellAmountDesc(Pageable pageable);

    // 검색
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' AND p.productName LIKE %:keyword% ")
    Page<ProductEntity> findByProductNameContaining(Pageable pageable, @Param("keyword") String keyword);

    // 전체 조회
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL'")
    Page<ProductEntity> findProducts(Pageable pageable);

    // 카테고리 조회
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' AND p.category = :category")
    Page<ProductEntity> findByCategory(@Param("category") String category, Pageable pageable);

    // 베스트 카테고리
    @Query("SELECT p FROM ProductEntity p INNER JOIN p.stockEntity s WHERE p.saleStatus = 'SELL' ORDER BY s.sellAmount DESC, p.createdAt DESC")
    Page<ProductEntity> findTop30ByOrderBySellAmountDesc(Pageable pageable);
}
