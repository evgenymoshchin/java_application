package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import com.gmail.evgenymoshchin.service.model.ItemShowPageDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;

import java.util.List;

public interface OrderService {
    ItemShowPageDTO findOrdersAndItemsWithPagination(int pageNumber, int pageSize);

    OrderShowDTO findOrderWithItemsByOrderId(Long id);

    List<OrderShowDTO> getOrders();

    void updateStatusByOrderId(Long id, StatusEnum statusEnum);

    void addItemToOrder(Integer itemsCount, Long itemId, String username);
}
