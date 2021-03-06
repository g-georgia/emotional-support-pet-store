package com.codecool.shop.controller.order;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = {"/order/product"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        String currentProductId = req.getQueryString();
        OrderItem selectedProduct = null;

        for (Product product : productDataStore.getAll() ) {
            if (product.getId() == Integer.parseInt(currentProductId)) {
                selectedProduct =  new OrderItem(product);
            }
        }

        orderDataStore.add(selectedProduct);

        PrintWriter out = resp.getWriter();

        Map<String, Object> params = new HashMap<>();
        params.put("products", orderDataStore.getAll());
        Object productListToJson = params.get("products");

        Gson gson = new Gson();
        String json = gson.toJson(productListToJson);

        out.println(json);

    }

}
