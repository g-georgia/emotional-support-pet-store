package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.OrderItem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = {"/order/product-to-increase"})
public class OrderItemIncrease extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        String currentOrderId = req.getQueryString();
        OrderItem orderToIncrease = null;

        for (OrderItem order : orderDataStore.getAll() ) {
            if (order.getId() == Integer.parseInt(currentOrderId)) {
                orderToIncrease =  new OrderItem(order);
            }
        }

        assert orderToIncrease != null;

        orderDataStore.increaseOrderItem(currentOrderId);

        PrintWriter out = resp.getWriter();

        Map<String, Object> params = new HashMap<>();
        params.put("products", orderDataStore.getAll());
        Object productListToJson = params.get("products");

        Gson gson = new Gson();
        String json = gson.toJson(productListToJson);

        out.println(json);

    }

}

