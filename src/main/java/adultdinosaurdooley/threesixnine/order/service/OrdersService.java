package adultdinosaurdooley.threesixnine.order.service;

import adultdinosaurdooley.threesixnine.cart.dto.GetCartDto;
import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.cart.service.exception.CartErrorCode;
import adultdinosaurdooley.threesixnine.cart.service.exception.CartException;
import adultdinosaurdooley.threesixnine.order.dto.SellProductDto;
import adultdinosaurdooley.threesixnine.order.entity.OrderDetail;
import adultdinosaurdooley.threesixnine.order.entity.Orders;
import adultdinosaurdooley.threesixnine.order.repository.OrderDetailRepository;
import adultdinosaurdooley.threesixnine.order.repository.OrdersRepository;
import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public SellProductDto ordersList(Long userId, Pageable pageable) {

        Orders validedUser = validUser(userId);

        Page<OrderDetail> orderDetailPage = orderDetailRepository.findAllByOrdersId(validedUser.getId(), pageable);

        List<SellProductDto.ResponseOrderProduct> orderProductList = orderDetailPage.stream().map(orderDetail -> SellProductDto.ResponseOrderProduct.builder()
                .orderDetailId(orderDetail.getId())
                .productName(orderDetail.getProduct().getName())
                .orderedAmount(orderDetail.getOrderedAmount())
                .orderedPrice(orderDetail.getOrderedPrice())
                .orderedSize(orderDetail.getProduct().getSize())

                .build()).collect(Collectors.toList());
        // 5. CartCheck 객체를 생성하고 반환합니다.
        return SellProductDto.from(validedUser, orderProductList);
    }

    private Orders validUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
    }
}
