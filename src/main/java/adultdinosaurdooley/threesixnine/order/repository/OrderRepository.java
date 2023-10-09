package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByUserEntityId(Long userId);
}
