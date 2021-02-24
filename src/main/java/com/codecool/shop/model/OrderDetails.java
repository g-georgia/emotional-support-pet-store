package com.codecool.shop.model;


import java.util.Currency;
import java.util.List;

public class OrderDetails {

    private List<OrderItem> orders;
    private double total;
    private Currency currency;

    public OrderDetails() {
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
