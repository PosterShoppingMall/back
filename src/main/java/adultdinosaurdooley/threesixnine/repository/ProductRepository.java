package adultdinosaurdooley.threesixnine.repository;

import adultdinosaurdooley.threesixnine.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 검색
    @Query("SELECT p FROM Product p INNER JOIN p.stock s")
    Page<Product> findByProductNameContaining(Pageable pageable, String keyword);

    // 전체 조회
    @Query("SELECT p FROM Product p INNER JOIN p.stock s")
    Page<Product> findProducts(Pageable pageable);

    // 카테고리 조회
    Page<Product> findByCategory(Pageable pageable, String category);

    // 베스트 카테고리
    @Query("SELECT p FROM Product p INNER JOIN p.stock s ORDER BY s.sellAmount DESC")
    Page<Product> findTop30ByOrderBySellAmountDesc(Pageable pageable);
}
