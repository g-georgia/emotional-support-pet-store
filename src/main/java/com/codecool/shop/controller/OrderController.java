package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;

@WebServlet(urlPatterns = {"/order/product"})
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDaoMem orderDataStore = OrderDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        String currentProductString = req.getQueryString();
        Product selectedProduct = null;

        for (Product product : productDataStore.getAll() ) {
            if (product.getName().equals(currentProductString)) {
                selectedProduct = product;
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
