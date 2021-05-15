package com.gmail.evgenymoshchin.service;

import com.gmail.evgenymoshchin.service.model.ItemDTO;
import com.gmail.evgenymoshchin.service.model.ItemPageDTO;

import java.util.List;

public interface ItemService {
    ItemPageDTO findItemsWithPagination(int pageNumber, int pageSize);

    List<ItemDTO> getItems();

    ItemDTO findItemById(Long id);

    void addItem(ItemDTO itemDTO);

    void removeItemById(Long id);
}
