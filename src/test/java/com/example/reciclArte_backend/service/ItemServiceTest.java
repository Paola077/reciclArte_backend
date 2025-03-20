package com.example.reciclArte_backend.service;

import com.example.reciclArte_backend.entity.Item;
import com.example.reciclArte_backend.exceptions.ItemNotFoundException;
import com.example.reciclArte_backend.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "Table", "Wooden table", "Furniture", "Used",
                "https://example.com/table.jpg", "Valencia, Spain", false);
    }

    @Test
    void saveItem_ShouldReturnSavedItem() {
        Mockito.when(itemRepository.save(item)).thenReturn(item);

        Item savedItem = itemService.saveItem(item);

        assertNotNull(savedItem);
        assertEquals(item.getId(), savedItem.getId());
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void getAllItems_ShouldReturnListOfItems() {
        List<Item> itemList = Arrays.asList(item);
        Mockito.when(itemRepository.findAll()).thenReturn(itemList);

        List<Item> result = itemService.getAllItems();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        Mockito.verify(itemRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getAllItems_ShouldThrowExceptionWhenNoItemsFound() {
        Mockito.when(itemRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ItemNotFoundException.class, itemService::getAllItems);

        assertEquals("No items found.", exception.getMessage());
        Mockito.verify(itemRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getItemById_ShouldReturnItem() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Optional<Item> result = itemService.getItemById(1L);

        assertTrue(result.isPresent());
        assertEquals(item.getId(), result.get().getId());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getItemById_ShouldThrowExceptionIfNotExists() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(1L));

        assertEquals("The item with id 1 does not exist.", exception.getMessage());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void markAsReserved_ShouldReturnUpdatedItem() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item updatedItem = itemService.markAsReserved(1L);

        assertTrue(updatedItem.isReserved());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }

    @Test
    void markAsReserved_ShouldThrowExceptionIfItemNotExists() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ItemNotFoundException.class, () -> itemService.markAsReserved(1L));

        assertEquals("The item with id 1 does not exist.", exception.getMessage());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void updateItem_ShouldReturnUpdatedItem() {
        Item updatedItem = new Item(1L, "New Table", "Updated description", "Furniture", "New",
                "https://example.com/new-table.jpg", "Madrid, Spain", true);

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        Item result = itemService.updateItem(1L, updatedItem);

        assertEquals("New Table", result.getName());
        assertEquals("Updated description", result.getDescription());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).save(any(Item.class));
    }

    @Test
    void updateItem_ShouldThrowExceptionIfNotExists() {
        Item updatedItem = new Item(1L, "New Table", "Updated description", "Furniture", "New",
                "https://example.com/new-table.jpg", "Madrid, Spain", true);

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(1L, updatedItem));

        assertEquals("The item with id 1 does not exist.", exception.getMessage());
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteItem_ShouldDeleteItem() {
        Mockito.when(itemRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(itemRepository).deleteById(1L);

        assertDoesNotThrow(() -> itemService.deleteItem(1L));

        Mockito.verify(itemRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteItem_ShouldThrowExceptionIfNotExists() {
        Mockito.when(itemRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(1L));

        assertEquals("The item with id 1 does not exist.", exception.getMessage());
        Mockito.verify(itemRepository, Mockito.times(1)).existsById(1L);
    }
}