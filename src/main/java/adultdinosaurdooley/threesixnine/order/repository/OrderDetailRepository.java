package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}
