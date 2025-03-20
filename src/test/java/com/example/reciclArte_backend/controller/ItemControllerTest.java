package com.example.reciclArte_backend.controller;

import com.example.reciclArte_backend.entity.Item;
import com.example.reciclArte_backend.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    void createItem_ShouldReturnSavedItem() throws Exception {
        String jsonRequest = """
            {
                "id": 13,
                "name": "Wooden Table",
                "description": "A small wooden table in good condition.",
                "category": "Furniture",
                "itemCondition": "Used",
                "imgUrl": "https://example.com/chair.jpg",
                "location": "Valencia, Spain",
                "reserved": false
            }
        """;

        String jsonResponse = jsonRequest;

        Item item = new Item(13L, "Wooden Table", "A small wooden table in good condition.",
                "Furniture", "Used", "https://example.com/chair.jpg", "Valencia, Spain", false);
        Mockito.when(itemService.saveItem(any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/api/items")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void getAllItems_ShouldReturnListOfItems() throws Exception {
        Item item1 = new Item(13L, "Wooden Table", "A small wooden table in good condition.",
                "Furniture", "Used", "https://example.com/chair.jpg", "Valencia, Spain", false);
        Item item2 = new Item(14L, "Office Chair", "Ergonomic office chair, black color.",
                "Furniture", "Used", "https://example.com/office-chair.jpg", "Valencia, Spain", true);

        Mockito.when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        String jsonResponse = """
            [
                {
                    "id": 13,
                    "name": "Wooden Table",
                    "description": "A small wooden table in good condition.",
                    "category": "Furniture",
                    "itemCondition": "Used",
                    "imgUrl": "https://example.com/chair.jpg",
                    "location": "Valencia, Spain",
                    "reserved": false
                },
                {
                    "id": 14,
                    "name": "Office Chair",
                    "description": "Ergonomic office chair, black color.",
                    "category": "Furniture",
                    "itemCondition": "Used",
                    "imgUrl": "https://example.com/office-chair.jpg",
                    "location": "Valencia, Spain",
                    "reserved": true
                }
            ]
        """;

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void getItemById_ShouldReturnItemIfExists() throws Exception {
        Item item = new Item(13L, "Wooden Table", "A small wooden table in good condition.",
                "Furniture", "Used", "https://example.com/chair.jpg", "Valencia, Spain", false);
        Mockito.when(itemService.getItemById(13L)).thenReturn(Optional.of(item));

        String jsonResponse = """
            {
                "id": 13,
                "name": "Wooden Table",
                "description": "A small wooden table in good condition.",
                "category": "Furniture",
                "itemCondition": "Used",
                "imgUrl": "https://example.com/chair.jpg",
                "location": "Valencia, Spain",
                "reserved": false
            }
        """;

        mockMvc.perform(get("/api/items/13"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void deleteItem_ShouldCallServiceMethod() throws Exception {
        Mockito.doNothing().when(itemService).deleteItem(13L);

        mockMvc.perform(delete("/api/items/13"))
                .andExpect(status().isOk());

        Mockito.verify(itemService, times(1)).deleteItem(13L);
    }

    @Test
    void markAsReserved_ShouldReturnUpdatedItem() throws Exception {
        Item item = new Item(13L, "Wooden Table", "A small wooden table in good condition.",
                "Furniture", "Used", "https://example.com/chair.jpg", "Valencia, Spain", true);
        Mockito.when(itemService.markAsReserved(13L)).thenReturn(item);

        String jsonResponse = """
            {
                "id": 13,
                "name": "Wooden Table",
                "description": "A small wooden table in good condition.",
                "category": "Furniture",
                "itemCondition": "Used",
                "imgUrl": "https://example.com/chair.jpg",
                "location": "Valencia, Spain",
                "reserved": true
            }
        """;

        mockMvc.perform(put("/api/items/13/reserve"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void updateItem_ShouldReturnUpdatedItem() throws Exception {
        String jsonRequest = """
            {
                "id": 13,
                "name": "Wooden Desk",
                "description": "A small wooden desk, great for working from home.",
                "category": "Furniture",
                "itemCondition": "Used",
                "imgUrl": "https://example.com/desk.jpg",
                "location": "Valencia, Spain",
                "reserved": false
            }
        """;

        String jsonResponse = jsonRequest;

        Item updatedItem = new Item(13L, "Wooden Desk", "A small wooden desk, great for working from home.",
                "Furniture", "Used", "https://example.com/desk.jpg", "Valencia, Spain", false);
        Mockito.when(itemService.updateItem(eq(13L), any(Item.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/api/items/13")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

}