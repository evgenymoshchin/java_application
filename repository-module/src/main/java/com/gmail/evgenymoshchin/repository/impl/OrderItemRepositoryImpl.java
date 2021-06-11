package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.OrderItemRepository;
import com.gmail.evgenymoshchin.repository.model.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepositoryImpl extends GenericRepositoryImpl<Long, OrderItem> implements OrderItemRepository {
}
