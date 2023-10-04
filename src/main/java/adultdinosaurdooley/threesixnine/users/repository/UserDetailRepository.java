package adultdinosaurdooley.threesixnine.users.repository;

import adultdinosaurdooley.threesixnine.users.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    UserDetail findByUserId(long userId);


}