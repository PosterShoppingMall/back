package adultdinosaurdooley.threesixnine.order.controller;

import adultdinosaurdooley.threesixnine.cart.service.CartService;
import adultdinosaurdooley.threesixnine.order.dto.OrderAddressDTO;
import adultdinosaurdooley.threesixnine.order.dto.OrderRequestDTO;
import adultdinosaurdooley.threesixnine.order.dto.PurchasedProductDTO;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.order.service.OrderService;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.jwt.JwtTokenProvider;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/369/order")
public class OrderController {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{userId}")
    public ResponseEntity order(@PathVariable("userId") long userId,
                                @RequestBody OrderRequestDTO orderRequestDTO) {


        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new OrderException(OrderErrorCode.USER_NOT_FOUND));


        Long orderId = cartService.orderCartProduct(orderRequestDTO.getCartOrderDTO(), userEntity);
        orderService.updateAddress(orderRequestDTO.getOrderAddressDTO(), orderId);

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    //주문하는 유저의 배송정보 가져오기
    @GetMapping("/address/{userId}")
    public ResponseEntity<OrderAddressDTO> getUserAddressInfo(@PathVariable Long userId) {
        System.out.println("userId = " + userId);

        return orderService.findAddress(userId);
    }


    //주문내역 조회
    @GetMapping
    public ResponseEntity<List<PurchasedProductDTO>> purchaseHistoryList(
            HttpServletRequest request,
            @RequestParam (name = "page", defaultValue = "1") int page,
            @RequestParam (name = "size", required = false, defaultValue = "5") int size) {

        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = jwtTokenProvider.getUserPK(header);
        return ResponseEntity.ok(orderService.purchaseHistoryList(userId, page-1, size));
    }


}