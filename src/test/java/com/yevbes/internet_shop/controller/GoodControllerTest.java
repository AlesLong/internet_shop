package com.yevbes.internet_shop.controller;

import com.yevbes.internet_shop.model.Good;
import com.yevbes.internet_shop.service.GoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GoodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GoodService goodService;

    @InjectMocks
    private GoodController goodController;

    private Good good;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(goodController).build();
        good = new Good("Sample Good", 100.0, 10); // Initialize a sample Good object
    }

    @Test
    void testShowAllGoods() throws Exception {

        when(goodService.showAllGoods()).thenReturn(List.of(good));

        mockMvc.perform(get("/api/goods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Good"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(10));

        verify(goodService, times(1)).showAllGoods();
    }

    @Test
    void testGetGoodById() throws Exception {

        when(goodService.getGoodById(1L)).thenReturn(good);

        mockMvc.perform(get("/api/goods/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Good"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(goodService, times(1)).getGoodById(1L);
    }

    @Test
    void testAddGood() throws Exception {

        when(goodService.addGood(any(Good.class))).thenReturn(good);

        mockMvc.perform(post("/api/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sample Good\", \"price\":100.0, \"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Good"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(goodService, times(1)).addGood(any(Good.class));
    }

    @Test
    void testUpdateGood() throws Exception {

        Good updatedGood = new Good("Updated Good", 120.0, 15);
        when(goodService.updateGood(1L, updatedGood)).thenReturn(updatedGood);

        mockMvc.perform(put("/api/goods/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Good\", \"price\":120.0, \"quantity\":15}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Good"))
                .andExpect(jsonPath("$.price").value(120.0))
                .andExpect(jsonPath("$.quantity").value(15));

        verify(goodService, times(1)).updateGood(1L, updatedGood);
    }
}
