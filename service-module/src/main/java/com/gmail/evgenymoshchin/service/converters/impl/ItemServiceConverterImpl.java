package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.service.converters.ItemServiceConverter;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemServiceConverterImpl implements ItemServiceConverter {

    @Override
    public ItemDTO convertItemToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setUuid(item.getUuid());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        return itemDTO;
    }

    @Override
    public Item convertDTOtoItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item.setDescription(itemDTO.getDescription());
        return item;
    }
}
