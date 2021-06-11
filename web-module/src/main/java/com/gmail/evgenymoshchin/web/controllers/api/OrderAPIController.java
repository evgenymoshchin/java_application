package com.gmail.evgenymoshchin.web.controllers.api;

import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderAPIController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderShowDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public OrderShowDTO getOrderById(@PathVariable Long id) {
        return orderService.findOrderWithItemsByOrderId(id);
    }
}
