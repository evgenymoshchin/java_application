package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.repository.model.Order;
import com.gmail.evgenymoshchin.service.model.ItemShowDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;

public interface OrderServiceConverter {
    OrderShowDTO convertOrderToOrderShowDTO(Order order);

    ItemShowDTO convertOrderToItemShowDTO(Order order, Item item);
}
