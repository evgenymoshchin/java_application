package com.gmail.evgenymoshchin.service.converters.impl;

import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ItemServiceConverterImplTest {

    @InjectMocks
    private ItemServiceConverterImpl itemServiceConverter;

    @Test
    void shouldConvertItemToDTOAndReturnNotNullObject() {
        Item item = new Item();
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertNotNull(itemDTO);
    }

    @Test
    void shouldConvertItemToDTOAndReturnCorrectId() {
        Item item = new Item();
        Long testId = 1L;
        item.setId(testId);
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertEquals(testId, itemDTO.getId());
    }

    @Test
    void shouldConvertItemToDTOAndReturnCorrectName() {
        Item item = new Item();
        String name = "name";
        item.setName(name);
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertEquals(name, itemDTO.getName());
    }

    @Test
    void shouldConvertItemToDTOAndReturnCorrectDescription() {
        Item item = new Item();
        String description = "description";
        item.setDescription(description);
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertEquals(description, itemDTO.getDescription());
    }

    @Test
    void shouldConvertItemToDTOAndReturnCorrectUUID() {
        Item item = new Item();
        UUID uuid = UUID.randomUUID();
        item.setUuid(uuid);
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertEquals(uuid, itemDTO.getUuid());
    }

    @Test
    void shouldConvertItemToDTOAndReturnCorrectPrice() {
        Item item = new Item();
        BigDecimal price = new BigDecimal("15.21323124124");
        item.setPrice(price);
        ItemDTO itemDTO = itemServiceConverter.convertItemToDTO(item);
        Assertions.assertEquals(price, itemDTO.getPrice());
    }

    @Test
    void shouldConvertDTOToItemAndReturnNotNullObject() {
        ItemDTO itemDTO = new ItemDTO();
        Item item = itemServiceConverter.convertDTOtoItem(itemDTO);
        Assertions.assertNotNull(item);
    }

    @Test
    void shouldConvertDTOToItemAndReturnName() {
        ItemDTO itemDTO = new ItemDTO();
        String name = "name";
        itemDTO.setName(name);
        Item item = itemServiceConverter.convertDTOtoItem(itemDTO);
        Assertions.assertEquals(name, item.getName());
    }

    @Test
    void shouldConvertDTOToItemAndReturnDescription() {
        ItemDTO itemDTO = new ItemDTO();
        String description = "description";
        itemDTO.setDescription(description);
        Item item = itemServiceConverter.convertDTOtoItem(itemDTO);
        Assertions.assertEquals(description, item.getDescription());
    }

    @Test
    void shouldConvertDTOToItemAndReturnPrice() {
        ItemDTO itemDTO = new ItemDTO();
        BigDecimal price = new BigDecimal("15.21323124124");
        itemDTO.setPrice(price);
        Item item = itemServiceConverter.convertDTOtoItem(itemDTO);
        Assertions.assertEquals(price, item.getPrice());
    }
}

