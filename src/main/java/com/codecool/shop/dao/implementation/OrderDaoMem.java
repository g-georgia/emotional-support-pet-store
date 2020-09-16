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
                lineItem.subtotalPrice = (float) (Math.round((lineItem.quantity * lineItem.getDefaultPrice())*100.0)/100.0);
                return;
            }
        }
        orderList.add(product);
    }

    @Override
    public List<OrderItem> getAll() {
        return orderList;
    }

    public void increaseOrderItem(String orderId) {
        for (int i=0; i < orderList.size(); i++ ) {
            if (orderList.get(i).getId() == Integer.parseInt(orderId)) {
                orderList.get(i).increaseOrder();
            }
        }
    }

    public void decreaseOrderItem(String orderId) {
        for (int i=0; i < orderList.size(); i++ ) {
            if (orderList.get(i).getId() == Integer.parseInt(orderId)) {
                orderList.get(i).decreaseOrder();
                if (orderList.get(i).quantity <= 0) {
                    orderList.remove(orderList.get(i));
                }
            }
        }

    }




}
