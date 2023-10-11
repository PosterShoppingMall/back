package adultdinosaurdooley.threesixnine.cart.service;


import adultdinosaurdooley.threesixnine.cart.dto.CartDTO;
import adultdinosaurdooley.threesixnine.cart.dto.CartOrderDTO;
import adultdinosaurdooley.threesixnine.cart.dto.CartProductListDTO;
import adultdinosaurdooley.threesixnine.cart.entity.CartEntity;
import adultdinosaurdooley.threesixnine.cart.repository.CartRepository;
import adultdinosaurdooley.threesixnine.cart.dto.CartProductDTO;
import adultdinosaurdooley.threesixnine.cart.entity.CartProductEntity;
import adultdinosaurdooley.threesixnine.cart.repository.CartProductRepository;
import adultdinosaurdooley.threesixnine.order.dto.OrderDTO;
import adultdinosaurdooley.threesixnine.order.entity.OrderDetailEntity;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.order.repository.OrderDetailRepository;
import adultdinosaurdooley.threesixnine.order.repository.OrderRepository;
import adultdinosaurdooley.threesixnine.order.service.OrderService;
import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.repository.ProductRepository;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

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

    public Long orderCartProduct(CartOrderDTO CartOrderDTO, UserEntity userEntity){

//        OrderDetailEntity orderDetailEntity = orderRepository.findByUserEntityId(userEntity.getId());

        List<OrderDTO> orderDTOList = new ArrayList<>();


        for(Long cartOrderId: CartOrderDTO.getCartProductIdList()){
            CartProductEntity cartProductEntity = cartProductRepository
                    .findById(cartOrderId)
                    .orElseThrow(() -> new OrderException(OrderErrorCode.CART_PRODUCT_NOT_FOUND));
            System.out.println("cartOrderId :" + cartOrderId);

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setProductId(cartProductEntity.getProductEntity().getId());
            orderDTO.setOrderCount(cartProductEntity.getCartCnt());
            orderDTOList.add(orderDTO);
        }

        // 장바구니에 담은 상품을 주문하도록 주문 로직 호출
        Long orderId = orderService.orders(orderDTOList, userEntity);

        // 장바구니 주문한 상품 삭제
        for(Long cartOrderId: CartOrderDTO.getCartProductIdList()){
            CartProductEntity cartProductEntity = cartProductRepository
                    .findById(cartOrderId)
                    .orElseThrow(() -> new OrderException(OrderErrorCode.CART_PRODUCT_NOT_FOUND));
            cartProductRepository.delete(cartProductEntity);
        }

        return orderId;
    }

    public CartProductListDTO findCartProductList(long userId) {
        CartEntity validateCartEntity = validateCart(userId);

        List<CartProductEntity> cartProductEntityList = validateCartEntity.getCartProductEntities();

        List<CartProductListDTO.CartProduct> cartProductList;
        cartProductList = cartProductEntityList.stream().map(
                CartProductEntity -> {
                    String url = String.valueOf(CartProductEntity.getProductEntity().getProductImageEntity().get(0).getImagePath());
                    return CartProductListDTO.CartProduct.builder()
                            .productId(CartProductEntity.getProductEntity().getId())
                            .imageUrl(url)
                            .productName(CartProductEntity.getProductEntity().getProductName())
                            .cartProductAmount(CartProductEntity.getCartCnt())
                            .cartProductTotalPrice(CartProductEntity.getCartCnt() * CartProductEntity.getProductEntity().getProductPrice())
                            .productPrice(CartProductEntity.getProductEntity().getProductPrice())
                            .build();
                }).collect(Collectors.toList());
        return CartProductListDTO.from(validateCartEntity, cartProductList);
    }

    private CartEntity validateCart(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserEntityId(userId);
        if (cartEntity == null) {
            throw new OrderException(OrderErrorCode.USER_NOT_FOUND);
        }
        return cartEntity;
    }

}

