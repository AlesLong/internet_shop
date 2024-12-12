package com.yevbes.internet_shop.controller;

import com.yevbes.internet_shop.model.Order;
import com.yevbes.internet_shop.service.OrderService;
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

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        order = new Order();
        order.setId(1L);
    }

    @Test
    void testUpdateOrder() throws Exception {

        when(orderService.updateOrder(eq(1L), anyList())).thenReturn(order);

        mockMvc.perform(put("/api/orders/{orderId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(orderService, times(1)).updateOrder(eq(1L), anyList());
    }

    @Test
    void testPayForOrder() throws Exception {

        when(orderService.payForOrder(1L)).thenReturn(order);

        mockMvc.perform(put("/api/orders/{id}/pay", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(orderService, times(1)).payForOrder(1L);
    }

    @Test
    void testDeleteOrder() throws Exception {

        doNothing().when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrderById(1L);
    }

    @Test
    void testListAllOrders() throws Exception {

        when(orderService.listAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(orderService, times(1)).listAllOrders();
    }

    @Test
    void testGetOrderById() throws Exception {

        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(orderService, times(1)).getOrderById(1L);
    }
}
