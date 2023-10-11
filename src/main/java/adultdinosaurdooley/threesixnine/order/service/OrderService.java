package adultdinosaurdooley.threesixnine.order.service;


import adultdinosaurdooley.threesixnine.order.dto.OrderAddressDTO;
import adultdinosaurdooley.threesixnine.order.dto.OrderDTO;
import adultdinosaurdooley.threesixnine.order.entity.DeliveryInformation;
import adultdinosaurdooley.threesixnine.order.entity.OrderDetailEntity;
import adultdinosaurdooley.threesixnine.order.entity.OrderEntity;
import adultdinosaurdooley.threesixnine.order.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.exception.OrderException;
import adultdinosaurdooley.threesixnine.order.repository.DeliveryInformationRepository;
import adultdinosaurdooley.threesixnine.order.repository.OrderRepository;
import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.repository.ProductRepository;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeliveryInformationRepository deliveryInformationRepository;
    // 장바 구니 에서 주문할 상품 데이터 를 전달 받아서 주문 생성

    public Long orders(List<OrderDTO> orderDTOList, UserEntity userEntity){

        // orderDTO 로 OrderDetail 생성
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();

        // 주문할 상품 리스트 만듦
        for(OrderDTO orderDTO : orderDTOList){
            Long productId = orderDTO.getProductId();
            System.out.println(productRepository.findById(productId));
            Optional<ProductEntity> productOptional = productRepository.findById(productId);
            if(productOptional.isPresent()) {
                ProductEntity productEntity = productOptional.get();
                System.out.println(productEntity);

                OrderDetailEntity orderDetailEntity =
                        OrderDetailEntity.createOrderDetail(productEntity, orderDTO.getOrderCount());
                orderDetailEntityList.add(orderDetailEntity);
            } else {
                new OrderException(OrderErrorCode.CART_PRODUCT_NOT_FOUND);
            }
        }

        OrderEntity order = OrderEntity.createOrder(orderDetailEntityList, userEntity);
        orderRepository.save(order);

        return order.getId();
    }

    public ResponseEntity<OrderAddressDTO> findAddress(Long userId){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

        if(optionalUserEntity.isPresent()) {
            UserEntity userAddressInfo = optionalUserEntity.get();

            OrderAddressDTO orderAddressDTO = OrderAddressDTO.builder()
                    .userGetName(userAddressInfo.getName())
                    .userGetphoneNumber(userAddressInfo.getPhoneNumber())
                    .userGetDetailAddress(userAddressInfo.getDetailAddress())
                    .userGetRoadAddress(userAddressInfo.getRoadAddress())
                    .userGetPostCode(userAddressInfo.getPostCode())
                    .build();

            return ResponseEntity.status(200).body(orderAddressDTO);
        }
        return  null;

    }


    //배송정보 등록(주소변경)
    public void updateAddress(OrderAddressDTO orderAddressDTO,Long orderId) {


        //orderId에 해당되는 주문을 가져와야하지 않나?
       Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);

       if(optionalOrderEntity.isPresent()){
            OrderEntity orderEntity = optionalOrderEntity.get();

           //dto -> entity 변환
           DeliveryInformation deliveryInformation = DeliveryInformation.builder()
                   .orderEntity(orderEntity)
                   .deliveryAddress(orderAddressDTO.getUserGetRoadAddress())
                   .deliveryAddressDetail(orderAddressDTO.getUserGetDetailAddress())
                   .deliveryPostCode(orderAddressDTO.getUserGetPostCode())
                   .deliveryName(orderAddressDTO.getUserGetName())
                   .deliveryPhone(orderAddressDTO.getUserGetphoneNumber())
                   .build();

           orderEntity.setDeliveryInformation(deliveryInformation);
           //배송 db 저장

           orderRepository.save(orderEntity);
//           deliveryInformationRepository.save(deliveryInformation);
       }


    }
}
