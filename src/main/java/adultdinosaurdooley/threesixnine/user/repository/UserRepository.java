package adultdinosaurdooley.threesixnine.user.repository;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}