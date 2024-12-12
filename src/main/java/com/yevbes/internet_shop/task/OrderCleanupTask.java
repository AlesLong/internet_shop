package com.yevbes.internet_shop.task;

import com.yevbes.internet_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderCleanupTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(fixedRate = 60000)
    public void cleanUpUnpaidOrders() {
        orderService.deleteUnpaidOrders();
    }
}
