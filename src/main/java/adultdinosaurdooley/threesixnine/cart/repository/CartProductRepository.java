package adultdinosaurdooley.threesixnine.cart.repository;

import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    Page<CartProduct> findAllByCartId(Long cartId, Pageable pageable);

    List<CartProduct> findByCartId(Long cartId);

}