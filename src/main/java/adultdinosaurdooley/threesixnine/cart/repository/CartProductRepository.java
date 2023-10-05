package adultdinosaurdooley.threesixnine.cart.repository;

import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);

    Page<CartProduct> findAllByCartId(Long cartId, Pageable pageable);
}