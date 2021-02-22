package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(urlPatterns = {"/supplier"})
public class SupplierController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        String currentSupplierId = req.getQueryString();
        Supplier currentSupplier = null;

        for (Supplier supplier : supplierDataStore.getAll() ) {
            if (supplier.getId() == Integer.parseInt(currentSupplierId)) {
                currentSupplier = supplier;
            }
        }

        PrintWriter out = resp.getWriter();

        Map<String, Object> params = new HashMap<>();
        params.put("supplier", supplierDataStore.getAll());
        params.put("products", productDataStore.getBy(currentSupplier));

        Object productListToJson = params.get("products");

        Gson gson = new Gson();
        String json = gson.toJson(productListToJson);

        out.println(json);

    }

}
