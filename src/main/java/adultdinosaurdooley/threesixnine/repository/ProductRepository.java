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
    Page<Product> findByProductNameContainingOrderByIdDesc(Pageable pageable, String keyword);

    // 전체 조회
    Page<Product> findByOrderByIdDesc(Pageable pageable);
//    @Query("SELECT p FROM Product p INNER JOIN p.stock s order by s.sellAmount")
//    List<Product> findByOrderBySellAmount();


    // 카테고리 조회
    Page<Product> findByCategoryOrderByIdDesc(Pageable pageable, String category);
//    @Query("SELECT p FROM Product p INNER JOIN p.stock s where p.category = :category order by s.sellAmount")
//    List<Product> findByCategoryOrderBySellAmount(@Param("category") String category);

    // 베스트 카테고리
    @Query("SELECT p FROM Product p INNER JOIN p.stock s ORDER BY s.sellAmount DESC")
    Page<Product> findTop30ByOrderBySellAmountDesc(Pageable pageable);
}
