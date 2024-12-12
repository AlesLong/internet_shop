package com.yevbes.internet_shop.repository;

import com.yevbes.internet_shop.model.Order;
import com.yevbes.internet_shop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatusAndOrderTimeBefore(OrderStatus status, LocalDateTime cutoffTime);
}
