package com.yevbes.internet_shop.service;

import com.yevbes.internet_shop.model.Order;
import com.yevbes.internet_shop.model.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order> listAllOrders();

    Order getOrderById(Long id);

    Order createOrder(List<OrderItem> items);

    Order updateOrder(Long orderId, List<OrderItem> updatedItems);

    Order payForOrder(Long orderId);

    Order deleteOrderItem(Long orderId, Long itemId);

    void deleteOrderById(Long id);

    void deleteUnpaidOrders();
}
