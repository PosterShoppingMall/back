package adultdinosaurdooley.threesixnine.user.repository;

import adultdinosaurdooley.threesixnine.user.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByUserEntity_Id(Long id);
}
