package com.example.reciclArte_backend.service;

import com.example.reciclArte_backend.entity.Item;
import com.example.reciclArte_backend.exceptions.ItemNotFoundException;
import com.example.reciclArte_backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new ItemNotFoundException("No items found.");
        }
        return items;
    }

    public Optional<Item> getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if(optionalItem.isEmpty()) {
            throw new ItemNotFoundException("The item with id " + id + " does not exist.");
        }

        return optionalItem;
    }

    public Item markAsReserved(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The item with id " + id + " does not exist."));

        item.setReserved(true);
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The item with id " + id + " does not exist."));

        item.setName(updatedItem.getName());
        item.setDescription(updatedItem.getDescription());
        item.setCategory(updatedItem.getCategory());
        item.setItemCondition(updatedItem.getItemCondition());
        item.setImgUrl(updatedItem.getImgUrl());
        item.setLocation(updatedItem.getLocation());
        item.setReserved(updatedItem.isReserved());

        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ItemNotFoundException("The item with id " + id + " does not exist.");
        }
        itemRepository.deleteById(id);
    }
}
