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
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if(optionalItem.isEmpty()) {
            throw new ItemNotFoundException("The item with id " + id + " does not exist.");
        }

        return optionalItem;
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
}
