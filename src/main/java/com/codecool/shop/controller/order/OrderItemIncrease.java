package com.codecool.shop.controller.order;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/order/product-to-increase"})

public class OrderItemIncrease extends OrderItemChange {
    public OrderItemIncrease() {
        super(OrderDaoMem::increaseOrderItem);

    }

}

