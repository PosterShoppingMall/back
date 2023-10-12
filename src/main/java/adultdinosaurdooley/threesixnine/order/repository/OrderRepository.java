package adultdinosaurdooley.threesixnine.order.repository;

import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserEntityId(Long userId);

//    @Query("SELECT o FROM OrderEntity o WHERE o.userEntity.id = : findId")
//    List<OrderEntity> findByUserEntity(UserEntity findId);
}
