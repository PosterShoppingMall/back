package adultdinosaurdooley.threesixnine.cart.service;

import adultdinosaurdooley.threesixnine.cart.dto.GetCartDto;
import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.cart.repository.CartProductRepository;
import adultdinosaurdooley.threesixnine.cart.repository.CartRepository;
import adultdinosaurdooley.threesixnine.cart.service.exception.CartErrorCode;
import adultdinosaurdooley.threesixnine.cart.service.exception.CartException;

import adultdinosaurdooley.threesixnine.product.entity.ProductImageEntity;
import adultdinosaurdooley.threesixnine.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductImageRepository productImageRepository;

    public GetCartDto getCart(Long userId, Pageable pageable) {

        // 1. 주어진 userId를 사용하여 카트를 검증합니다.
        Cart validateCart = validateCart(userId);
        List<CartProduct> validateCartProduct = cartProductRepository.findByCartId(userId);
        // 2. 페이지네이션을 이용하여 해당 카트에 있는 제품 목록을 가져옵니다.
        Page<CartProduct> cartProductPage = cartProductRepository.findAllByCartId(validateCart.getId(), pageable);
        // 3. 카트에 있는 제품들의 총 가격을 계산합니다.
        int totalAmount = 0;
        for (CartProduct cartProduct : validateCartProduct) {
            totalAmount += cartProduct.getCartCnt() * cartProduct.getProduct().getPrice();
        }
        // 4. 카트에 있는 제품 정보를 GetCartDto.CartProduct 객체로 매핑하고 리스트로 만듭니다.
        List<GetCartDto.CartProduct> cartProductList = cartProductPage.getContent().stream()
                .map(getCart -> {
                    // 상품 이미지 리스트 가져오기
                    List<ProductImageEntity> productImages = getProductImagesForProduct(getCart.getProduct().getId());

                    // 첫 번째 이미지 경로 가져오기
                    String firstImagePath = productImages.isEmpty() ? "" : productImages.get(0).getImagePath();
                    return GetCartDto.CartProduct.builder()
                            .productId(getCart.getProduct().getId())
                            .productName(getCart.getProduct().getName())
                            .cartProductAmount(getCart.getCartCnt())
                            .cartProductAmountPrice(getCart.getCartCnt() * getCart.getProduct().getPrice()) // 3에서 계산한 총 가격을 설정합니다.
                            .productPrice(getCart.getProduct().getPrice())
                            .productImagePath(firstImagePath)
                            .build();
                })
                .collect(Collectors.toList());
        // 5. GetCartDto 객체를 생성하고 반환합니다.
        return GetCartDto.from(validateCart, cartProductList);
    }

    // 주어진 userId를 사용하여 카트를 검증하는 메서드
    private Cart validateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartException(CartErrorCode.USER_NOT_FOUND));
    }

    private List<ProductImageEntity> getProductImagesForProduct(Long productId) {
        // ProductImageRepository를 사용하여 productId에 해당하는 ProductImage 리스트 가져오기
        return productImageRepository.findByProductId(productId);
    }

}
