package com.gmail.evgenymoshchin.service.converters;

import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.service.model.ItemDTO;

public interface ItemServiceConverter {
    ItemDTO convertItemToDTO(Item item);

    Item convertDTOtoItem(ItemDTO itemDTO);
}
