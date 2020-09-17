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
import java.util.Currency;

@WebServlet(urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("orders", orderDataStore.getAll());
        double total=0;
        Currency currency = null;
        for (OrderItem orderItem : orderDataStore.getAll()) {
            total += orderItem.subtotalPrice;
            currency = orderItem.getDefaultCurrency();
        }
        context.setVariable("total", total);
        context.setVariable("currency", currency);
        engine.process("order-confirmation.html", context, resp.getWriter());

        clearCart(orderDataStore);
    }



    private void clearCart(OrderDao order){
        order.getAll().clear();
    }
}
