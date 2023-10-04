package adultdinosaurdooley.threesixnine.cart.service;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.cart.repository.CartProductRepository;
import adultdinosaurdooley.threesixnine.cart.repository.CartRepository;
import adultdinosaurdooley.threesixnine.product.entity.Product;
import adultdinosaurdooley.threesixnine.product.entity.Stock;
import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    // User 에게 장바구니 생성
    public void createCart(User user){
        Cart cart = Cart.createCart(user);
        cartRepository.save(cart);
    }


    //장바구니에 Product 추가하기
    @Transactional
    public void addCart(User user, Product product, int count){

        Cart cart = cartRepository.findByUserId(user.getId());

        // cart 가 비어있다면 생성.
        if(cart == null){
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        // CartProduct 생성
        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(),product.getId());

        // CartProduct 가 비어있다면 새로 생성.
        if(cartProduct == null){
            cartProduct = CartProduct.createCartProduct(cart,product,count);
            cartProductRepository.save(cartProduct);
            cart.setCount(cart.getCount() + 1);
        }else{
            // 비어있지 않다면 그만큼 갯수를 추가.
            cartProduct.addCount(count);
        }

    }

    //장바구니 조회
    public List<CartProduct> userCartView(Cart cart){
        List<CartProduct> cartProducts = cartProductRepository.findAll();
        List<CartProduct> userProducts = new ArrayList<>();

        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getCart().getId() == cart.getId()){
                userProducts.add(cartProduct);
            }
        }

        return userProducts;
    }

    //장바구니 Product 삭제
    public void cartProductDelete(long id){
        cartProductRepository.deleteById(id);
    }

    // 장바구니 Product 전체삭제
    public void cartDelete(long id){
        List<CartProduct> cartProducts = cartProductRepository.findAll(); // 전체 cart_item 찾기

        // 반복문을 이용하여 접속 User의 CartProduct 만 찾아서 삭제
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getCart().getUser().getId() == id){
                cartProduct.getCart().setCount(cartProduct.getCart().getCount() - 1);
                cartProductRepository.deleteById(cartProduct.getId());
            }
        }
    }

    @Transactional
    public void cartPayment(long id){
        List<CartProduct> cartProducts = cartProductRepository.findAll(); // 전체 cart_item 찾기
        List<CartProduct> userCartProducts = new ArrayList<>();
        User buyer = userRepository.findById(id).get();

        // 반복문을 이용하여 접속 User의 CartProduct만 찾아서 저장
        for(CartProduct cartProduct : cartProducts){
            if(cartProduct.getCart().getUser().getId() == buyer.getId()){
                userCartProducts.add(cartProduct);
            }
        }

        // 반복문을 이용하여 접속 User의 CartProduct 만 찾아서 삭제
        for(CartProduct cartProduct : userCartProducts){
            // 재고 변경
            int stock = cartProduct.getProduct().getStock(); // 현재 재고를 변수에 저장
            stock = stock - cartProduct.getCount(); // 저장된 변수를 결제한 갯수만큼 빼준다
            cartProduct.getProduct().setStock(stock); // 재고갯수 변경

//            // 금액 처리
//            User seller = cartProduct.getProduct().getUser();
//            int cash = cartProduct.getProduct().getPrice(); // 아이템 가격을 변수에 저장
//            buyer.setMoney(cash * -1);
//            seller.setMoney(cash);
        }
    }

}
