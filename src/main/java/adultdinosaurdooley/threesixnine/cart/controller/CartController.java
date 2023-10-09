package adultdinosaurdooley.threesixnine.cart.controller;

import adultdinosaurdooley.threesixnine.cart.dto.CartDTO;
import adultdinosaurdooley.threesixnine.cart.dto.CartProductDTO;
import adultdinosaurdooley.threesixnine.cart.service.CartService;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/369/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    // 장바구니 담기
    @PostMapping("/{userId}")
    public ResponseEntity<String> cart(@PathVariable("userId") @Positive long userId,
                                        @RequestBody @Valid CartDTO addCartDTO){

        System.out.println("userId = "+userId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("해당하는 사용자를 찾을 수 없습니다."));

        cartService.addCart(userEntity, addCartDTO);
        return ResponseEntity.ok("상품이 장바구니에 담겼습니다.");
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/{cartProductId}")
    public ResponseEntity<String> updateCartProduct(@PathVariable("cartProductId") @Positive long cartProductId,
                                                    @RequestBody @Valid CartProductDTO cartProductDTO){

        cartService.updateCartProductCnt(cartProductId, cartProductDTO);

        return ResponseEntity.ok("상품의 수량이 변경되었습니다.");
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<String> deleteCartProduct(@PathVariable("cartProductId")
                                                        @Positive long cartProductId){

        cartService.deleteCartProduct(cartProductId);

        return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
    }
}
