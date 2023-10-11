package adultdinosaurdooley.threesixnine.user.repository;

import adultdinosaurdooley.threesixnine.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Long> {
    Optional<RefreshToken> findByUserEntity_Id(Long id);
}
