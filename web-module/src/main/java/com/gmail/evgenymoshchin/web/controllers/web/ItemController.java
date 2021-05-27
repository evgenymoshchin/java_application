package com.gmail.evgenymoshchin.web.controllers.web;

import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.model.ItemCountDTO;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import com.gmail.evgenymoshchin.service.model.ItemPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.GET_ITEMS_REDIRECTION_URL;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;

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
        return GET_ITEMS_REDIRECTION_URL;
    }

    @GetMapping("/copy-item")
    public String copyItemById(@RequestParam("id") Long id) {
        itemService.copyItemById(id);
        return GET_ITEMS_REDIRECTION_URL;
    }

    @GetMapping("/add-item-to-order")
    public String getAddItemPage(@RequestParam("id") Long id, Model model,
            ItemCountDTO itemCountDTO) {
        itemCountDTO.setItemId(id);
        model.addAttribute("item", itemCountDTO);
        return "add_item_to_order_page";
    }

    @PostMapping("/add-item-to-order")
    public String AddItemToOrder(
            ItemCountDTO itemCountDTO,
            BindingResult bindingResult,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "add_item_to_order_page";
        } else {
            orderService.addItemToOrder(itemCountDTO.getItemCount(), itemCountDTO.getItemId(), principal.getName());
            return GET_ITEMS_REDIRECTION_URL;
        }
    }
}
