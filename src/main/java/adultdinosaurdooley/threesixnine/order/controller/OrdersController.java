package adultdinosaurdooley.threesixnine.order.controller;

import adultdinosaurdooley.threesixnine.order.dto.SellProductDto;
import adultdinosaurdooley.threesixnine.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/369")
public class OrdersController {

    private final OrderService orderService;
    @GetMapping("/order/{userId}")
    public ResponseEntity<SellProductDto> ordersList(
            @PathVariable("userId") Long userId,
            @RequestParam (name = "page", defaultValue = "1") int page,
            @RequestParam (name = "size", required = false, defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.ordersList(userId, page, size));
    }

}
