package com.example.reciclArte_backend.service;

import com.example.reciclArte_backend.entity.Item;
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
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item markAsReserved(Long id) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setReserved(true);
            return itemRepository.save(item);
        }
        return null;
    }

    public Item updateItem(Long id, Item updatedItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setCategory(updatedItem.getCategory());
            item.setItemCondition(updatedItem.getItemCondition());
            item.setImgUrl(updatedItem.getImgUrl());
            item.setLocation(updatedItem.getLocation());
            item.setReserved(updatedItem.isReserved());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
