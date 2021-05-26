package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.OrderRepository;
import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.repository.model.Order;
import com.gmail.evgenymoshchin.service.converters.OrderServiceConverter;
import com.gmail.evgenymoshchin.service.model.ItemForOrderDTO;
import com.gmail.evgenymoshchin.service.model.ItemShowDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderServiceConverterImpl implements OrderServiceConverter {

    private final OrderRepository orderRepository;

    @Override
    public OrderShowDTO convertOrderToOrderShowDTO(Order order) {
        OrderShowDTO orderShowDTO = new OrderShowDTO();
        orderShowDTO.setId(order.getId());
        orderShowDTO.setStatus(order.getStatus().getName());
        orderShowDTO.setItemsCount(orderRepository.getCountOfItemsByOrderId(order.getId()));
        orderShowDTO.setFirstName(order.getUser().getFirstName());
        orderShowDTO.setLastName(order.getUser().getLastName());
        orderShowDTO.setTelephone(order.getUser().getUserInformation().getTelephone());
        List<ItemForOrderDTO> itemOrderDTOS = new ArrayList<>();
        Set<Item> items = order.getItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Item item : items) {
            ItemForOrderDTO itemForOrder = new ItemForOrderDTO();
            itemForOrder.setName(item.getName());
            Long itemsCount = orderRepository.getCountOfItemsByOrderAndItemIds(order.getId(), item.getId());
            itemForOrder.setCount(itemsCount);
            itemForOrder.setPrice(calculateCost(itemsCount, item.getPrice()));
            totalPrice = totalPrice.add(new BigDecimal(String.valueOf(itemForOrder.getPrice())));
            itemOrderDTOS.add(itemForOrder);
        }
        orderShowDTO.setItems(itemOrderDTOS);
        orderShowDTO.setFinalPrice(totalPrice);
        return orderShowDTO;
    }

    @Override
    public ItemShowDTO convertOrderToItemShowDTO(Order order, Item item) {
        ItemShowDTO itemShow = new ItemShowDTO();
        itemShow.setName(item.getName());
        itemShow.setOrderId(order.getId());
        itemShow.setStatusEnum(order.getStatus().getName());
        Long itemsCount = orderRepository.getCountOfItemsByOrderAndItemIds(order.getId(), item.getId());
        itemShow.setCount(itemsCount);
        itemShow.setFinalPrice(calculateCost(itemsCount, item.getPrice()));
        return itemShow;
    }

    private BigDecimal calculateCost(Long itemQuantity, BigDecimal itemPrice) {
        BigDecimal itemCost = itemPrice.multiply(new BigDecimal(itemQuantity));
        BigDecimal totalCost = BigDecimal.ZERO;
        return totalCost.add(itemCost);
    }
}
