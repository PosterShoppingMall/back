package adultdinosaurdooley.threesixnine.order.controller;

import adultdinosaurdooley.threesixnine.cart.dto.CartOrderDTO;
import adultdinosaurdooley.threesixnine.cart.service.CartService;
import adultdinosaurdooley.threesixnine.order.dto.OrderAddressDTO;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.order.service.OrderService;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/369/order")
public class OrderController {

    @Getter
    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity order(@PathVariable("userId") long userId,
                                @RequestBody CartOrderDTO cartOrderDTO) {

        System.out.println("userId = " + userId);

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new OrderException(OrderErrorCode.USER_NOT_FOUND));


        Long orderId = cartService.orderCartProduct(cartOrderDTO, userEntity);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    //orderId 고민
    @PostMapping("/address/{orderId}")
    public ResponseEntity<String> updateAddress(@PathVariable("orderId") long orderId,
                                                @RequestBody OrderAddressDTO orderAddressDTO) {

        orderService.updateAddress(orderAddressDTO, orderId);
        return ResponseEntity.ok("배송정보가 등록되었습니다.");
    }


    @GetMapping("/address/{userId}")
    public ResponseEntity<OrderAddressDTO> getUserAddressInfo(@PathVariable Long userId) {
        System.out.println("userId = " + userId);

        return orderService.findAddress(userId);
    }
}