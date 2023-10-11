package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

    Page<OrderDetailEntity> findAllByOrdersId(Long orderId, Pageable pageable);

    List<OrderDetailEntity> findByOrdersId(Long cartId);

}
