package com.codecool.shop.dao.implementation;


import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.dao.OrderDao;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDaoMem implements OrderDao {
    
    private List<Product> orderList = new ArrayList<>();
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
    public void add(Product product) {
        orderList.add(product);
    }

    @Override
    public List<Product> getAll() {
        return orderList;
    }




}
