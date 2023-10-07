package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.order.entity.Orders;
import adultdinosaurdooley.threesixnine.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByUserId(Long userId);
}
