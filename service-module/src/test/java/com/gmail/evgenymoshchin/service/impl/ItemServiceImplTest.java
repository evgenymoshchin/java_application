package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ItemRepository;
import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.service.converters.ItemServiceConverter;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemServiceConverter converter;
    @InjectMocks
    ItemServiceImpl itemService;

    @Test
    void shouldReturnEmptyList() {
        List<ItemDTO> items = itemService.getItems();

        assertTrue(items.isEmpty());
    }

    @Test
    void shouldReturnItemsList() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1L);
        Item item = new Item();

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
        when(converter.convertItemToDTO(item)).thenReturn(itemDTO);

        List<ItemDTO> items = itemService.getItems();

        assertEquals(items.get(0).getId(), itemDTO.getId());
    }
}