package adultdinosaurdooley.threesixnine.cart.repository;

import adultdinosaurdooley.threesixnine.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    // 유저가 장바구니를 갖고있는가
    CartEntity findByUserEntityId(Long userId);
//    Optional<CartEntity> findByUserEntityId(Long userId);
}
