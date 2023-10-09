package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 메인페이지
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL' ORDER BY p.createdAt DESC")
    List<Product> findTop6ByOrderByCreatedAt(Pageable pageable);
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL' AND p.category = :category ORDER BY p.createdAt DESC")
    List<Product> findTop6ByCategoryOrderByCreatedAt(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL' ORDER BY s.sellAmount DESC")
    List<Product> findTop6ByOrderBySellAmountDesc(Pageable pageable);

    // 검색
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL'")
    Page<Product> findByProductNameContaining(Pageable pageable, String keyword);

    // 전체 조회
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL'")
    Page<Product> findProducts(Pageable pageable);

    // 카테고리 조회
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL' AND p.category = :category")
    Page<Product> findByCategory(@Param("category") String category, Pageable pageable);

    // 베스트 카테고리
    @Query("SELECT p FROM Product p INNER JOIN p.stock s WHERE p.saleStatus = 'SELL' ORDER BY s.sellAmount DESC")
    Page<Product> findTop30ByOrderBySellAmountDesc(Pageable pageable);
}
