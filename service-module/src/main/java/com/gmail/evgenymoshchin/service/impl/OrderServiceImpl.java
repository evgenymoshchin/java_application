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
import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.converters.OrderServiceConverter;
import com.gmail.evgenymoshchin.service.exception.ServiceException;
import com.gmail.evgenymoshchin.service.model.ItemShowDTO;
import com.gmail.evgenymoshchin.service.model.ItemShowPageDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.ONE_VALUE;
import static com.gmail.evgenymoshchin.service.constants.ExceptionConstants.ORDER_NOT_FOUND_MESSAGE;
import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
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
        itemShowDTOS.sort(Comparator.comparing(ItemShowDTO::getDate).reversed());
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
        if (Objects.nonNull(order)) {
            return converter.convertOrderToOrderShowDTO(order);
        } else {
            throw new ServiceException(String.format(ORDER_NOT_FOUND_MESSAGE, id));
        }
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
        if (Objects.nonNull(order)) {
            Status status = statusRepository.findStatusByName(statusEnum);
            order.setStatus(status);
        } else {
            throw new ServiceException(String.format(ORDER_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    @Transactional
    public void addItemToOrder(Integer itemsCount, Long itemId, String username) {
        Order orderFromDatabase = orderRepository.findOrderByUsername(username);
        if (Objects.nonNull(orderFromDatabase) && orderFromDatabase.getStatus().getName().equals(StatusEnum.NEW)) {
            Long orderId = orderFromDatabase.getId();
            saveOrderItemByItemsCount(itemsCount, itemId, orderId);
        } else {
            Order order = new Order();
            order.setStatus(statusRepository.findStatusByName(StatusEnum.NEW));
            order.setUser(userRepository.findByUsername(username));
            order.setCreatedBy(LocalDate.now());
            orderRepository.persist(order);
            Long orderId = order.getId();
            saveOrderItemByItemsCount(itemsCount, itemId, orderId);
        }
    }

    private void saveOrderItemByItemsCount(Integer itemsCount, Long itemId, Long orderId) throws ServiceException {
        for (int i = ONE_VALUE; i <= itemsCount; i++) {
            OrderItem orderItem = new OrderItem();
            if (itemExist(itemId)) {
                orderItem.setItemId(itemId);
                orderItem.setOrderId(orderId);
                orderItemRepository.persist(orderItem);
            }
        }
    }

    private boolean itemExist(Long itemId) {
        return itemService.findItemById(itemId) != null;
    }
}
