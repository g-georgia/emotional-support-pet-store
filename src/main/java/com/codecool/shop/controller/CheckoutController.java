package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.OrderItem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        int orderCount = 0;
        double orderTotalPrice = 0;
        for (OrderItem order : orderDataStore.getAll()) {
            orderCount+=order.quantity;
            orderTotalPrice+=order.subtotalPrice;
        }

        context.setVariable("orders", orderDataStore.getAll());
        context.setVariable("orderCount", orderCount);
        context.setVariable("orderTotalPrice", Math.round(orderTotalPrice*100.0)/100.0);

        engine.process("checkout_form.html", context, resp.getWriter());

    }

}
