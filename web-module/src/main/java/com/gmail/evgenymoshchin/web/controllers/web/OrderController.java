package com.gmail.evgenymoshchin.web.controllers.web;

import com.gmail.evgenymoshchin.repository.model.StatusEnum;
import com.gmail.evgenymoshchin.service.OrderService;
import com.gmail.evgenymoshchin.service.StatusService;
import com.gmail.evgenymoshchin.service.model.ItemShowPageDTO;
import com.gmail.evgenymoshchin.service.model.OrderShowDTO;
import com.gmail.evgenymoshchin.service.model.StatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_SIZE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.DEFAULT_PAGE_VALUE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE;
import static com.gmail.evgenymoshchin.web.constants.ControllersConstants.PAGE_SIZE;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final StatusService statusService;

    @GetMapping("/show")
    public String getItemsWithOrders(Model model,
                                     @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE_VALUE) int pageSize,
                                     @RequestParam(value = PAGE, defaultValue = DEFAULT_PAGE_VALUE) int pageNumber
    ) {
        ItemShowPageDTO itemPage = orderService.findOrdersAndItemsWithPagination(pageNumber, pageSize);
        model.addAttribute("itemPage", itemPage);
        return "get_all_orders_items";
    }

    @GetMapping("/show-order-by-id")
    public String getItemWithOrderByOrderId(@RequestParam Long id, Model model) {
        OrderShowDTO order = orderService.findOrderWithItemsByOrderId(id);
        model.addAttribute("order", order);
        return "get_order_by_id";
    }

    @GetMapping("/update-status")
    public String getUpdateStatusPage(@RequestParam Long id, Model model) {
        OrderShowDTO orderShowDTO = orderService.findOrderWithItemsByOrderId(id);
        model.addAttribute("orderShowDTO", orderShowDTO);
        List<StatusDTO> statuses = statusService.findAll();
        statuses.removeIf(status -> status.getName().equals(orderShowDTO.getStatus()));
        model.addAttribute("statuses", statuses);
        return "update_order";
    }

    @PostMapping("/update-status")
    public String updateStatusByOrderId(OrderShowDTO orderShowDTO,
                                        @RequestParam(value = "status") StatusEnum statusEnum) {
        orderService.updateStatusByOrderId(orderShowDTO.getId(), statusEnum);
        return "redirect:/orders/show";
    }
}
