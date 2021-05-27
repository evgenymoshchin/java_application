package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.ItemRepository;
import com.gmail.evgenymoshchin.repository.model.Item;
import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.converters.ItemServiceConverter;
import com.gmail.evgenymoshchin.service.model.ItemCountDTO;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import com.gmail.evgenymoshchin.service.model.ItemPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gmail.evgenymoshchin.service.util.ServiceUtil.getNumbersOfPages;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemServiceConverter converter;

    @Override
    @Transactional
    public ItemPageDTO findItemsWithPagination(int pageNumber, int pageSize) {
        ItemPageDTO itemPage = new ItemPageDTO();
        List<Item> items = itemRepository.findWithPagination(pageNumber, pageSize);
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : items) {
            itemDTOS.add(converter.convertItemToDTO(item));
        }
        itemDTOS.sort(Comparator.comparing(ItemDTO::getName));
        itemPage.getItems().addAll(itemDTOS);
        Long countOfArticles = itemRepository.getCount();
        itemPage.setPagesCount(countOfArticles);
        List<Integer> numbersOfPages = getNumbersOfPages(pageSize, countOfArticles);
        itemPage.getNumbersOfPages().addAll(numbersOfPages);
        return itemPage;
    }

    @Override
    @Transactional
    public List<ItemDTO> getItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : items) {
            itemDTOS.add(converter.convertItemToDTO(item));
        }
        return itemDTOS;
    }

    @Override
    @Transactional
    public ItemDTO findItemById(Long id) {
        Item item = itemRepository.findById(id);
        return converter.convertItemToDTO(item);
    }

    @Override
    @Transactional
    public void addItem(ItemDTO itemDTO) {
        itemRepository.persist(converter.convertDTOtoItem(itemDTO));
    }

    @Override
    @Transactional
    public void removeItemById(Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.remove(item);
    }

    @Override
    @Transactional
    public void copyItemById(Long id) {
        Item item = itemRepository.findById(id);
        Item copiedItem = new Item();
        copiedItem.setName(item.getName());
        copiedItem.setPrice(item.getPrice());
        copiedItem.setDescription(item.getDescription());
        itemRepository.persist(copiedItem);
    }

    @Override
    @Transactional
    public ItemCountDTO findItemCountById(Long id) {
//        Item item = itemRepository.findById(id);
//        ItemCountDTO itemCountDTO = new ItemCountDTO();
//        itemCountDTO.setItemId(item.getId());
//        itemCountDTO.setItemCount();
        return null;
    }
}
