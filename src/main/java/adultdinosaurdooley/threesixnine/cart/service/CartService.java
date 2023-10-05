package adultdinosaurdooley.threesixnine.cart.service;

import adultdinosaurdooley.threesixnine.cart.dto.CartCheck;
import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.cart.repository.CartProductRepository;
import adultdinosaurdooley.threesixnine.cart.repository.CartRepository;
import adultdinosaurdooley.threesixnine.product.entity.Product;
import adultdinosaurdooley.threesixnine.product.entity.Stock;
import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.repository.UserRepository;
import adultdinosaurdooley.threesixnine.cart.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.text.DecimalFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    // User 에게 장바구니 생성
//    public void createCart(User user){
//        Cart cart = Cart.createCart(user);
//        cartRepository.save(cart);
//    }
//
//
//    //장바구니에 Product 추가하기
//    @Transactional
//    public void addCart(User user, Product product, int count){
//
//        Cart cart = validateCart(user.getId());
//
//        // cart 가 비어있다면 생성.
//        if(cart == null){
//            cart = Cart.createCart(user);
//            cartRepository.save(cart);
//        }
//
//        // CartProduct 생성
//        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(),product.getId());
//
//        // CartProduct 가 비어있다면 새로 생성.
//        if(cartProduct == null){
//            cartProduct = CartProduct.createCartProduct(cart,product,count);
//            cartProductRepository.save(cartProduct);
//            cart.setCount(cart.getCount() + 1);
//        }else{
//            // 비어있지 않다면 그만큼 갯수를 추가.
//            cartProduct.addCount(count);
//        }
//
//    }
//
//    //장바구니 조회
//    public List<CartProduct> userCartView(Cart cart){
//        List<CartProduct> cartProducts = cartProductRepository.findAll();
//        List<CartProduct> userProducts = new ArrayList<>();
//
//        for(CartProduct cartProduct : cartProducts){
//            if(cartProduct.getCart().getId() == cart.getId()){
//                userProducts.add(cartProduct);
//            }
//        }
//
//        return userProducts;
//    }
//
//    //장바구니 Product 삭제
//    public void cartProductDelete(long id){
//        cartProductRepository.deleteById(id);
//    }
//
//    // 장바구니 Product 전체삭제
//    public void cartDelete(long id){
//        List<CartProduct> cartProducts = cartProductRepository.findAll(); // 전체 cart_item 찾기
//
//        // 반복문을 이용하여 접속 User의 CartProduct 만 찾아서 삭제
//        for(CartProduct cartProduct : cartProducts){
//            if(cartProduct.getCart().getUser().getId() == id){
//                cartProduct.getCart().setCount(cartProduct.getCart().getCount() - 1);
//                cartProductRepository.deleteById(cartProduct.getId());
//            }
//        }
//    }
//
//    @Transactional
//    public void cartPayment(long id){
//        List<CartProduct> cartProducts = cartProductRepository.findAll(); // 전체 cart_item 찾기
//        List<CartProduct> userCartProducts = new ArrayList<>();
//        User buyer = userRepository.findById(id).get();
//
//        // 반복문을 이용하여 접속 User의 CartProduct만 찾아서 저장
//        for(CartProduct cartProduct : cartProducts){
//            if(cartProduct.getCart().getUser().getId() == buyer.getId()){
//                userCartProducts.add(cartProduct);
//            }
//        }
//
//        // 반복문을 이용하여 접속 User의 CartProduct 만 찾아서 삭제
//        for(CartProduct cartProduct : userCartProducts){
//            // 재고 변경
//            int stock = cartProduct.getProduct().getStock(); // 현재 재고를 변수에 저장
//            stock = stock - cartProduct.getCartCnt(); // 저장된 변수를 결제한 갯수만큼 빼준다
//            cartProduct.getProduct().setStock(stock); // 재고갯수 변경
//
////            // 금액 처리
////            User seller = cartProduct.getProduct().getUser();
////            int cash = cartProduct.getProduct().getPrice(); // 아이템 가격을 변수에 저장
////            buyer.setMoney(cash * -1);
////            seller.setMoney(cash);
//        }
//    }

    public CartCheck findByCartId(Long cartId, CartCheck cartCheck, Pageable pageable) {
        // 1. 주어진 cartId를 사용하여 카트를 검증
        Cart cart = validateCart(cartId);
        // 2. 페이지네이션을 이용하여 해당 카트에 있는 제품 목록을 가져옵니다.
        Page<CartProduct> cartProductPage = cartProductRepository.findAllByCartId(cartId, pageable);
        // 3. 카트에 있는 제품들의 총 가격을 계산합니다.
        Integer totalAmount = cartCheck.getCartProducts().stream()
                .map(product -> product.getProductPrice().intValue() * product.getCartCnt())
                .mapToInt(Integer::intValue).sum();
        // 4. 카트에 있는 제품 정보를 CartCheck.CartProduct 객체로 매핑하고 리스트로 만듭니다.
        List<CartCheck.CartProduct> cartProductList = cartProductPage.getContent().stream().map(cartProduct -> CartCheck.CartProduct.builder()
                .productId(cartProduct.getProduct().getId())
                .productName(cartProduct.getProduct().getName())
                .cartCnt(cartProduct.getCartCnt())
                .cartProductAmount(totalAmount) // 3에서 계산한 총 가격을 설정합니다.
                .productPrice(cartProduct.getProduct().getPrice())
                .build()).collect(Collectors.toList());
        // 5. CartCheck 객체를 생성하고 반환합니다.
        return CartCheck.from(cart, cartProductList);
    }

    // 주어진 userId를 사용하여 카트를 검증하는 메서드
    private Cart validateCart(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(()-> new NotFoundException("USER 를 찾을 수가 없습니다."));
    }

    private static DecimalFormat formatter = new DecimalFormat("###,###");

}
