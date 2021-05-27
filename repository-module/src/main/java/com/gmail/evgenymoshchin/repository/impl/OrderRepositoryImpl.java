package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.OrderRepository;
import com.gmail.evgenymoshchin.repository.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.GET_COUNT_OF_ALL_ITEMS_IN_ORDER;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.GET_COUNT_OF_ONE_ITEM_IN_ORDER_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.GET_ORDER_BY_USERNAME_QUERY;
import static com.gmail.evgenymoshchin.repository.impl.constant.QueryConstants.USERNAME_VALUE;

@Log4j2
@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public Long getCountOfItemsByOrderAndItemIds(Long orderId, Long itemId) {
        Query query = entityManager.createQuery(GET_COUNT_OF_ONE_ITEM_IN_ORDER_QUERY);
        query.setParameter("orderId", orderId);
        query.setParameter("itemId", itemId);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCountOfItemsByOrderId(Long id) {
        Query query = entityManager.createQuery(GET_COUNT_OF_ALL_ITEMS_IN_ORDER);
        query.setParameter("id", id);
        return (Long) query.getSingleResult();
    }

    @Override
    public Order findOrderByUsername(String username) {
        Long id = 1L;
        Query query = entityManager.createQuery(GET_ORDER_BY_USERNAME_QUERY);
        query.setParameter(USERNAME_VALUE, username);
        query.setParameter("id", id);
        try {
            return (Order) query.getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
