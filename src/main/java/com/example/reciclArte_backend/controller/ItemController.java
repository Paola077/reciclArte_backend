package com.example.reciclArte_backend.controller;

import com.example.reciclArte_backend.entity.Item;
import com.example.reciclArte_backend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/{id}/reserve")
    public Item markAsReserved(@PathVariable Long id) {
        return itemService.markAsReserved(id);
    }

}
