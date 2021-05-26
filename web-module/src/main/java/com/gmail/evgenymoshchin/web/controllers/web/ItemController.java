package com.gmail.evgenymoshchin.web.controllers.web;

import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import com.gmail.evgenymoshchin.service.model.ItemPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/get")
    public String getItems(Model model,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber
    ) {
        ItemPageDTO itemPage = itemService.findItemsWithPagination(pageNumber, pageSize);
        model.addAttribute("itemPage", itemPage);
        return "get_all_items";
    }

    @GetMapping("/show-item-by-id")
    public String getItemById(@RequestParam("id") Long id, Model model) {
        ItemDTO item = itemService.findItemById(id);
        model.addAttribute("item", item);
        return "get_item_by_id";
    }

    @GetMapping("/delete-item-by-id")
    public String removeItemById(@RequestParam("id") Long id) {
        if (id != null) {
            itemService.removeItemById(id);
        }
        return "redirect:/items/get";
    }

    @GetMapping("/copy-item")
    public String copyItemById(@RequestParam("id") Long id) {
        itemService.copyItemById(id);
        return "redirect:/items/get";
    }
}
