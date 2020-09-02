package com.codecool.shop.dao.implementation;


import com.codecool.shop.model.OrderItem;
import com.codecool.shop.dao.OrderDao;


import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    
    private List<OrderItem> orderList = new ArrayList<>();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(OrderItem product) {
        for ( OrderItem lineItem : orderList ) {
            if (lineItem.getName().equals(product.getName())) {
                lineItem.quantity += 1;
                lineItem.subtotalPrice = lineItem.quantity * lineItem.getDefaultPrice();
                return;
            }
        }
        orderList.add(product);
    }

    @Override
    public List<OrderItem> getAll() {
        return orderList;
    }




}
