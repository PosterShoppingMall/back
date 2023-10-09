package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    Page<OrderDetail> findAllByOrdersId(Long orderId, Pageable pageable);

}
