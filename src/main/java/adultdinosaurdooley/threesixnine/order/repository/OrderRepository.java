package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUserId(Long userId);
}
