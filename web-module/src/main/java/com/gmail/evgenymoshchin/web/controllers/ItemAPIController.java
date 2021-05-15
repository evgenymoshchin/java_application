package com.gmail.evgenymoshchin.web.controllers;

import com.gmail.evgenymoshchin.service.ItemService;
import com.gmail.evgenymoshchin.service.model.ItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemAPIController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDTO> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addArticle(@RequestBody @Valid ItemDTO item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            itemService.addItem(item);
            log.info("Added item with name {}", item.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        itemService.removeItemById(id);
        log.info("Removed item with id {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
