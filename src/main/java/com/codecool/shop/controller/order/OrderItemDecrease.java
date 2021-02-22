package com.codecool.shop.controller.order;

import com.codecool.shop.dao.implementation.OrderDaoMem;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/order/product-to-decrease"})

public class OrderItemDecrease extends OrderItemChange {

    public OrderItemDecrease() {
        super(OrderDaoMem::decreaseOrderItem);
    }

}

