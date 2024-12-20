package com.yevbes.internet_shop.service;

import com.yevbes.internet_shop.exception.*;
import com.yevbes.internet_shop.model.Good;
import com.yevbes.internet_shop.model.Order;
import com.yevbes.internet_shop.model.OrderItem;
import com.yevbes.internet_shop.model.OrderStatus;
import com.yevbes.internet_shop.repository.GoodRepository;
import com.yevbes.internet_shop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GoodRepository goodRepository;

    @Override
    @Transactional
    public List<Order> listAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        logger.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Order createOrder(List<OrderItem> items) {
        logger.info("Attempting create new order");
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setOrderTime(LocalDateTime.now());
        logger.info("Set items:{}", items);
        order.setItems(new ArrayList<>());

        for (OrderItem item : items) {

            Good good = goodRepository.findById(item.getGood().getId())
                    .orElseThrow(() -> new GoodNotFoundException("Good not found"));

            if (good.getQuantity() < item.getQuantity()) {
                throw new NotEnoughQuantityOfItemException("Not enough stock for " + good.getName());
            }
            logger.info("Change good quantity in storage");
            good.setQuantity(good.getQuantity() - item.getQuantity());
            goodRepository.save(good);

            item.setGood(good);
            item.setOrder(order);
            order.getItems().add(item);
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order updateOrder(Long orderId, List<OrderItem> updatedItems) {
        logger.info("Attempting update order");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        for (OrderItem updatedItem : updatedItems) {
            boolean itemExists = false;
            for (OrderItem existingItem : order.getItems()) {
                if (existingItem.getGood().getId().equals(updatedItem.getGood().getId())) {

                    Good good = goodRepository.findById(updatedItem.getGood().getId())
                            .orElseThrow(() -> new GoodNotFoundException("Good not found"));

                    if (good.getQuantity() < updatedItem.getQuantity()) {
                        throw new NotEnoughQuantityOfItemException("Not enough stock for " + good.getName());
                    }

                    good.setQuantity(good.getQuantity() - (updatedItem.getQuantity() - existingItem.getQuantity()));
                    goodRepository.save(good);

                    existingItem.setQuantity(updatedItem.getQuantity());
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                Good good = goodRepository.findById(updatedItem.getGood().getId())
                        .orElseThrow(() -> new RuntimeException("Good not found"));

                if (good.getQuantity() < updatedItem.getQuantity()) {
                    throw new NotEnoughQuantityOfItemException("Not enough stock for " + good.getName());
                }

                good.setQuantity(good.getQuantity() - updatedItem.getQuantity());
                goodRepository.save(good);

                updatedItem.setGood(good);
                order.getItems().add(updatedItem);
            }
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order payForOrder(Long orderId) {
        logger.info("Attempting pay for order");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new AlreadyPaidException("Order is already paid");
        }

        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order deleteOrderItem(Long orderId, Long itemId) {
        logger.info("Attempting delete item in order");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        OrderItem itemToRemove = order.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order item not found"));

        Good good = itemToRemove.getGood();
        good.setQuantity(good.getQuantity() + itemToRemove.getQuantity());
        goodRepository.save(good);

        order.getItems().remove(itemToRemove);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        logger.info("Attempting delete order with id:{}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new PaidOrderCannotBeDeletedException("Cannot delete a paid order");
        }

        updateGoodsQuantities(order);

        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public void deleteUnpaidOrders() {
        logger.info("Attempting delete unpaid orders");
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(10);
        List<Order> unpaidOrders = orderRepository.findByStatusAndOrderTimeBefore(OrderStatus.CREATED, cutoffTime);

        if (!unpaidOrders.isEmpty()) {
            unpaidOrders.forEach(this::updateGoodsQuantities);
            orderRepository.deleteAll(unpaidOrders);
        }
    }

    @Transactional
    private void updateGoodsQuantities(Order order) {

        for (OrderItem itemOrdered : order.getItems()) {
            Good goods = itemOrdered.getGood();
            goods.setQuantity(goods.getQuantity() + itemOrdered.getQuantity());
            goodRepository.save(goods);
        }
    }
}
