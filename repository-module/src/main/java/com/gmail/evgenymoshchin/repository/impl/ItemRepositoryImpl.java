package com.gmail.evgenymoshchin.repository.impl;

import com.gmail.evgenymoshchin.repository.ItemRepository;
import com.gmail.evgenymoshchin.repository.model.Item;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {
}
