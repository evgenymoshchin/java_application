package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.OrderItemRepository;
import com.gmail.evgenymoshchin.repository.OrderRepository;
import com.gmail.evgenymoshchin.repository.StatusRepository;
import com.gmail.evgenymoshchin.repository.UserRepository;
import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.repository.model.Order;
import com.gmail.evgenymoshchin.repository.model.OrderItem;
import com.gmail.evgenymoshchin.repository.model.Status;
import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.converters.OrderServiceConverter;
import com.gmail.evgenymoshchin.service.model.ItemShowDTO;
import com.gmail.evgenymoshchin.service.model.ItemShowPageDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderServiceConverter converter;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public ItemShowPageDTO findOrdersAndItemsWithPagination(int pageNumber, int pageSize) {
        ItemShowPageDTO itemShowPage = new ItemShowPageDTO();
        List<Order> orders = orderRepository.findWithPagination(pageNumber, pageSize);
        List<ItemShowDTO> itemShowDTOS = new ArrayList<>();
        for (Order order : orders) {
            if (Objects.nonNull(order.getItems())) {
                Set<Item> items = order.getItems();
                for (Item item : items) {
                    ItemShowDTO itemShow = converter.convertOrderToItemShowDTO(order, item);
                    itemShowDTOS.add(itemShow);
                }
            }
        }
//        orderDTOS.sort(Comparator.comparing(ArticleDTO::getDate).reversed());
        itemShowPage.getItems().addAll(itemShowDTOS);
        Long countOfOrders = orderRepository.getCount();
        itemShowPage.setPagesCount(countOfOrders);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfOrders);
        itemShowPage.getNumbersOfPages().addAll(numbersOfPages);
        return itemShowPage;
    }

    @Override
    @Transactional
    public OrderShowDTO findOrderWithItemsByOrderId(Long id) {
        Order order = orderRepository.findById(id);
        return converter.convertOrderToOrderShowDTO(order);
    }

    @Override
    @Transactional
    public List<OrderShowDTO> getOrders() {
        List<Order> ordersFromDataBase = orderRepository.findAll();
        List<OrderShowDTO> orders = new ArrayList<>();
        for (Order order : ordersFromDataBase) {
            OrderShowDTO orderShowDTO = converter.convertOrderToOrderShowDTO(order);
            orders.add(orderShowDTO);
        }
        return orders;
    }

    @Override
    @Transactional
    public void updateStatusByOrderId(Long id, StatusEnum statusEnum) {
        Order order = orderRepository.findById(id);
        Status status = statusRepository.findStatusByName(statusEnum);
        order.setStatus(status);
    }

    @Override
    @Transactional
    public void addItemToOrder(int itemsCount, Long itemId, String username) {
        Order orderFromDatabase = orderRepository.findOrderByUsername(username);
        if (orderFromDatabase.getStatus().getName() == StatusEnum.NEW) {
            Long orderId = orderFromDatabase.getId();
            saveOrderItemByItemsCount(itemsCount, itemId, orderId);
        } else {
            Order order = new Order();
            order.setStatus(statusRepository.findStatusByName(StatusEnum.NEW));
            order.setUser(userRepository.findByUsername(username));
            order.setFinalPrice(BigDecimal.ZERO);
            orderRepository.persist(order);
            Long orderId = order.getId();
            saveOrderItemByItemsCount(itemsCount, itemId, orderId);
        }
    }

    private void saveOrderItemByItemsCount(int itemsCount, Long itemId, Long orderId) {
        for (int i = 1; i <= itemsCount; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(itemId);
            orderItem.setOrderId(orderId);
            orderItemRepository.persist(orderItem);
        }
    }
}
