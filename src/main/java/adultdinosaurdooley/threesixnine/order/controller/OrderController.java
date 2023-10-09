package adultdinosaurdooley.threesixnine.order.controller;

import adultdinosaurdooley.threesixnine.cart.dto.CartOrderDTO;
import adultdinosaurdooley.threesixnine.cart.service.CartService;
import adultdinosaurdooley.threesixnine.order.dto.OrderDTO;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.order.service.OrderService;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/369/order)")
public class OrderController {

    private final CartService cartService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity order (@PathVariable("userId")long userId,
                                 @RequestBody CartOrderDTO cartOrderDTO){
        System.out.println("userId = " + userId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new OrderException(OrderErrorCode.USER_NOT_FOUND));

        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();

        if(cartOrderDTOList == null || cartOrderDTOList.size() == 0){
            return new ResponseEntity<String>("주문 할 상품을 선택해주세요",HttpStatus.FORBIDDEN);
        }

        Long orderId = cartService.orderCartProduct(cartOrderDTOList, userEntity);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);

    }
}
