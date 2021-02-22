package com.codecool.shop.controller.order;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.OrderItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderItemChange extends HttpServlet {

    private final BiConsumer<OrderDaoMem, String> orderItemChanger;

    public OrderItemChange(BiConsumer<OrderDaoMem, String> orderItemChanger) {
        this.orderItemChanger = orderItemChanger;
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        String currentOrderId = req.getQueryString();
        OrderItem orderToIncrease = null;

        for (OrderItem order : orderDataStore.getAll() ) {
            if (order.getId() == Integer.parseInt(currentOrderId)) {
                orderToIncrease =  new OrderItem(order);
            }
        }

        assert orderToIncrease != null;

        orderItemChanger.accept(orderDataStore, currentOrderId);

        PrintWriter out = resp.getWriter();

        Map<String, Object> params = new HashMap<>();
        params.put("products", orderDataStore.getAll());
        Object productListToJson = params.get("products");

        Gson gson = new Gson();
        String json = gson.toJson(productListToJson);

        out.println(json);

    }

}