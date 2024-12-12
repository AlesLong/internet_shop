package com.yevbes.internet_shop.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Good good;

    @Column(nullable = false)
    private int quantity;


    public OrderItem() {
    }

    public OrderItem(Good good, Order order, int quantity) {
        this.good = good;
        this.order = order;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(id, orderItem.id) && Objects.equals(good, orderItem.good) && Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        return 41 * Objects.hash(id, good, order, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", good=" + good +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}