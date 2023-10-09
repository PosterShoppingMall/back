package adultdinosaurdooley.threesixnine.cart.controller;

import adultdinosaurdooley.threesixnine.cart.dto.GetCartDto;
import adultdinosaurdooley.threesixnine.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/369")
@RestController
@Slf4j
public class CartController {

    private final CartService cartService;
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<GetCartDto> getCart(@PathVariable("cartId") Long userId, Pageable pageable) {


        return ResponseEntity.ok(cartService.getCart(userId, pageable));
    }
}
