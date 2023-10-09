package adultdinosaurdooley.threesixnine.order.controller;

import adultdinosaurdooley.threesixnine.order.dto.SellProductDto;
import adultdinosaurdooley.threesixnine.order.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/369")
public class OrdersController {

    private final OrdersService orderService;
    @GetMapping("/order/{userId}")
    public ResponseEntity<SellProductDto> ordersList(@PathVariable("userId") Long userId, Pageable pageable) {
        return ResponseEntity.ok(orderService.ordersList(userId, pageable));
    }
}
