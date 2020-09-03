package com.codecool.shop.dao;

import com.codecool.shop.model.OrderItem;

import java.util.List;

public interface OrderDao {

    void add(OrderItem product);

    List<OrderItem> getAll();



}
