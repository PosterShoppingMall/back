package adultdinosaurdooley.threesixnine.order.service;

import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.order.dto.SellProductDto;
import adultdinosaurdooley.threesixnine.order.entity.OrderDetail;
import adultdinosaurdooley.threesixnine.order.entity.Orders;
import adultdinosaurdooley.threesixnine.order.repository.OrderDetailRepository;
import adultdinosaurdooley.threesixnine.order.repository.OrdersRepository;
import adultdinosaurdooley.threesixnine.order.service.exception.OrderErrorCode;
import adultdinosaurdooley.threesixnine.order.service.exception.OrderException;
import adultdinosaurdooley.threesixnine.product.entity.ProductImage;
import adultdinosaurdooley.threesixnine.product.repository.ProductImageRepository;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final ProductImageRepository productImageRepository;

    public SellProductDto ordersList(Long userId, int page, int size) {
        // 사용자 유효성 검사를 통해 사용자 확인
        Orders validateUser = validUser(userId);

        Pageable pageable = PageRequest.of(page,size);

         // 주문 상세 내역 페이지 가져오기
        Page<OrderDetail> orderDetailPage = orderDetailRepository.findAllByOrdersId(validateUser.getId(), pageable);

        // 주문 상세 내역을 SellProductDto.ResponseOrderProduct로 변환
        List<SellProductDto.ResponseOrderProduct> orderProductList = orderDetailPage.getContent().stream()
                .map(orderDetail -> {
                    // 상품 이미지 리스트 가져오기
                    List<ProductImage> productImages = getProductImagesForProduct(orderDetail.getProduct().getId());

                    // 첫 번째 이미지 경로 가져오기
                    String firstImagePath = "";
                    if (!productImages.isEmpty()) {
                        firstImagePath = productImages.get(0).getImagePath();
                    }

                    return SellProductDto.ResponseOrderProduct.builder()
                            .productId(orderDetail.getProduct().getId())
                            .productName(orderDetail.getProduct().getName())
                            .orderedAmount(orderDetail.getOrderedAmount())
                            .orderedPrice(orderDetail.getOrderedPrice())
                            .totalOrderedPrice(orderDetail.getOrderedAmount() * orderDetail.getOrderedPrice())
                            .orderedSize(orderDetail.getProduct().getSize())
                            .orderedImagePath(firstImagePath)
                            .build();
                })
                .collect(Collectors.toList());
        // SellProductDto 객체 생성하고 반환
        return SellProductDto.from(validateUser, orderProductList);
    }

    // 사용자 유효성 검사를 수행하여 사용자 반환
    private Orders validUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.USER_NOT_FOUND));
    }

    // 상품 이미지 리스트를 productId에 따라 가져오는 메서드
    private List<ProductImage> getProductImagesForProduct(Long productId) {
        // ProductImageRepository를 사용하여 productId에 해당하는 ProductImage 리스트 가져오기
        return productImageRepository.findByProductId(productId);
    }
}
