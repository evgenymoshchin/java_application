package com.gmail.evgenymoshchin.repository;

import com.gmail.evgenymoshchin.repository.model.Order;

public interface OrderRepository extends GenericRepository<Long, Order> {
    Long getCountOfItemsByOrderAndItemIds(Long orderId, Long itemId);

    Long getCountOfItemsByOrderId(Long id);

    Order findOrderByUsername(String username);
}
