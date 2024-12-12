package com.yevbes.internet_shop.controller;

import com.yevbes.internet_shop.model.Order;
import com.yevbes.internet_shop.model.OrderItem;
import com.yevbes.internet_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody List<OrderItem> items) {
        return orderService.createOrder(items);
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable Long orderId, @RequestBody List<OrderItem> updatedItems) {
        return orderService.updateOrder(orderId, updatedItems);
    }

    @PutMapping("/{id}/pay")
    public Order payForOrder(@PathVariable Long id) {
        return orderService.payForOrder(id);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public Order deleteOrderItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.deleteOrderItem(orderId, itemId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }

    @GetMapping
    public List<Order> listAllOrders() {
        return orderService.listAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
