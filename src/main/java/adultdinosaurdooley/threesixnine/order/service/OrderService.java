package adultdinosaurdooley.threesixnine.order.service;

import adultdinosaurdooley.threesixnine.admin.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.admin.entity.Stock;
import adultdinosaurdooley.threesixnine.admin.repository.ProductRepository;
import adultdinosaurdooley.threesixnine.cart.dto.CartOrderDTO;
import adultdinosaurdooley.threesixnine.order.repository.OrderRepository;
import adultdinosaurdooley.threesixnine.order.dto.OrderDTO;
import adultdinosaurdooley.threesixnine.order.entity.OrderDetailEntity;
import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    // 장바 구니 에서 주문할 상품 데이터 를 전달 받아서 주문 생성
    public Long orders(List<OrderDTO> orderDTOList, UserEntity userEntity){

//        OrderEntity orderEntity = orderRepository.findByUserEntityId(userEntity.getId());

        // orderDTO 로 OrderItem 생성
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();

        // 주문할 상품 리스트 만듦
        for(OrderDTO orderDTO : orderDTOList){
            ProductEntity productEntity = productRepository.findById(orderDTO.getProductId())
                                                            .orElseThrow(() -> new OrderException(OrderErrorCode.PRODUCT_NOT_FOUND));

            OrderDetailEntity orderDetailEntity =
                    OrderDetailEntity.createOrderDetail(productEntity, orderDTO.getOrderCount());
            orderDetailEntityList.add(orderDetailEntity);
        }

        OrderEntity order = OrderEntity.createOrder(orderDetailEntityList, userEntity);
        orderRepository.save(order);

        return order.getId();
    }

}
