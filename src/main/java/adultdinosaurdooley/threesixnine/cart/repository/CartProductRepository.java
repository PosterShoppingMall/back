package adultdinosaurdooley.threesixnine.cart.repository;

import adultdinosaurdooley.threesixnine.cart.entity.CartProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProductEntity, Long> {

    // 상품이 장바구니에 들어있는가
    CartProductEntity findByCartEntityIdAndProductEntityId(Long cartId, Long productId);

    Page<CartProductEntity> findAllByCartEntityId(Long id, Pageable pageable);
    Page<CartProductEntity> findAllByCartEntityUserEntityId(Long id,Pageable pageable);
}
