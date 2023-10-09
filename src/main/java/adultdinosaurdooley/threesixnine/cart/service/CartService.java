package adultdinosaurdooley.threesixnine.cart.service;


import adultdinosaurdooley.threesixnine.cart.dto.CartDTO;
import adultdinosaurdooley.threesixnine.cart.entity.CartEntity;
import adultdinosaurdooley.threesixnine.cart.repository.CartRepository;
import adultdinosaurdooley.threesixnine.cart.dto.CartProductDTO;
import adultdinosaurdooley.threesixnine.cart.entity.CartProductEntity;
import adultdinosaurdooley.threesixnine.cart.repository.CartProductRepository;
import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.repository.ProductRepository;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Long addCart(UserEntity userEntity, CartDTO cartDTO){

        //유저 id로 유저의 장바구니 찾기
        CartEntity cartEntity = cartRepository.findByUserEntityId(userEntity.getId());

        // 장바구니가 없다면
        if(cartEntity == null){
            cartEntity = CartEntity.createCart(userEntity);
            cartRepository.save(cartEntity);
        }

        // 장바구니에 담을 상품을 조회
        ProductEntity productEntity = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(EntityNotFoundException::new);

        // 현재 상품이 장바구니에 있는가
        CartProductEntity cartProductEntity =
                cartProductRepository.findByCartEntityIdAndProductEntityId(cartEntity.getId(), productEntity.getId());

        if ( cartProductEntity == null){
            cartProductEntity = CartProductEntity.createCartProduct(cartEntity, productEntity, cartDTO.getCartCnt());
            cartProductRepository.save(cartProductEntity);
        }

        else {
            cartProductEntity.addCartProduct(cartDTO.getCartCnt());
        }
        return cartProductEntity.getId();
    }


    public CartProductDTO updateCartProductCnt(Long cartProductId, CartProductDTO updateCnt){

        Optional<CartProductEntity> cartProduct = cartProductRepository.findById(cartProductId);

        if(cartProduct.isPresent()){
            CartProductEntity existCartProductEntity = cartProduct.get();

            existCartProductEntity.setCartCnt(updateCnt.getCartCnt());

            existCartProductEntity = cartProductRepository.save(existCartProductEntity);

            return CartProductDTO.toCartProductDTO(existCartProductEntity);
        }
        else return null;
    }


    public void deleteCartProduct(Long cartProductId){
        CartProductEntity cartProductEntity = cartProductRepository.findById(cartProductId).orElseThrow(EntityNotFoundException::new);

        cartProductRepository.delete(cartProductEntity);
    }

}
