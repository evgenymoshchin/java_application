package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.OrderRepository;
import com.gmail.evgenymoshchin.repository.model.Article;
import com.gmail.evgenymoshchin.repository.model.Order;
import com.gmail.evgenymoshchin.service.converters.OrderServiceConverter;
import com.gmail.evgenymoshchin.service.model.ArticleDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderServiceConverter converter;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void shouldReturnEmptyList() {
        List<OrderShowDTO> orders = orderService.getOrders();

        assertTrue(orders.isEmpty());
    }

    @Test
    void shouldReturnOrdersList() {
        OrderShowDTO orderShowDTO = new OrderShowDTO();
        Long id = 1L;
        orderShowDTO.setId(id);
        Order order = new Order();

        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        when(converter.convertOrderToOrderShowDTO(order)).thenReturn(orderShowDTO);

        List<OrderShowDTO> orders = orderService.getOrders();

        assertEquals(orders.get(0).getId(), orderShowDTO.getId());
    }

    @Test
    void shouldFindOrderWithItemsByOrderId() {
        OrderShowDTO orderShowDTO = new OrderShowDTO();
        Long id = 1L;
        Order order = new Order();
        order.setId(id);

        when(orderRepository.findById(id)).thenReturn(order);
        when(converter.convertOrderToOrderShowDTO(order)).thenReturn(orderShowDTO);

        assertEquals(orderService.findOrderWithItemsByOrderId(order.getId()), orderShowDTO);
    }
}