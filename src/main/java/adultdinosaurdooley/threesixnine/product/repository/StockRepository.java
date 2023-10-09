package adultdinosaurdooley.threesixnine.product.repository;

import adultdinosaurdooley.threesixnine.product.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Long> {
}
