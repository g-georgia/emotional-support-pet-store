package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = {"/product-category"})
public class ProductCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        String currentProductCategoryId = req.getQueryString();
        ProductCategory ProductCategory = null;

        for (ProductCategory productCat : productCategoryDataStore.getAll() ) {
            if (productCat.getId() == Integer.parseInt(currentProductCategoryId)) {
                ProductCategory = productCat;
            }
        }

        PrintWriter out = resp.getWriter();

        Map<String, Object> params = new HashMap<>();
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getBy(ProductCategory));
        params.put("selectedCategory", ProductCategory.getName());

        Object productListToJson = params.get("products");

        Gson gson = new Gson();
        String json = gson.toJson(productListToJson);
        String category = gson.toJson(params.get("selectedCategory"));

        out.println(json);

    }

}
