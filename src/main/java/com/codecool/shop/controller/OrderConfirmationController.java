package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.OrderItem;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Currency;

import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("orders", orderDataStore.getAll());
        double total = 0;
        Currency currency = null;
        for (OrderItem orderItem : orderDataStore.getAll()) {
            total += orderItem.subtotalPrice;
            currency = orderItem.getDefaultCurrency();
        }
      
        context.setVariable("total", total);
        context.setVariable("currency", currency);
        engine.process("order-confirmation.html", context, resp.getWriter());

        writeFileToJSON(orderDataStore);

        String emailBody = ("<h1>Emotional Support Pet Store</h1>" +
                "<p><b>Thank you for your order, here are all your details:</b></p>" +
                "<table style=\"width:50%; border: 1px solid black;\">\n" +
                "  <tr style=\"border: 1px solid black;\">\n" +
                "    <th style=\"border: 1px solid black;\">ID</th>\n" +
                "    <th style=\"border: 1px solid black;\">Name</th>\n" +
                "    <th style=\"border: 1px solid black;\">Quantity</th>\n" +
                "    <th style=\"border: 1px solid black;\">Price</th>\n" +
                "    <th style=\"border: 1px solid black;\">Description</th>\n" +
                "  </tr>");
        for (OrderItem orderItem : orderDataStore.getAll()) {
            emailBody += "<tr>\n" +
                    "    <td style=\"border: 1px solid black;\">" + orderItem.getId() + "</td>\n" +
                    "    <td style=\"border: 1px solid black;\">" + orderItem.getName() + "</td>\n" +
                    "    <td style=\"border: 1px solid black;\">" + orderItem.quantity + "</td>\n" +
                    "    <td style=\"border: 1px solid black;\">" + orderItem.subtotalPrice + "</td>\n" +
                    "    <td style=\"border: 1px solid black;\">" + orderItem.getDescription() + "</td>\n" +
                    "  </tr>";
        }
        emailBody += "</table>";

        String recipient = "kristof910@gmail.com"; // delete
        EmailController.sendEmail(recipient, emailBody);

        clearCart(orderDataStore);
    }

    private void writeFileToJSON(OrderDao order) {
        JSONArray orderList = new JSONArray();
        for (OrderItem orderItem : order.getAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("OrderItemId", orderItem.getId());
            jsonObject.put("name", orderItem.getName());
            jsonObject.put("quantity", orderItem.quantity);
            jsonObject.put("subtotalPrice", orderItem.subtotalPrice);
            jsonObject.put("defaultPrice", orderItem.getDefaultPrice());
            jsonObject.put("defaultCurrency", orderItem.getDefaultCurrency());
            jsonObject.put("description", orderItem.getDescription());
            orderList.add(jsonObject);
        }

    	try (FileWriter file = new FileWriter("src/main/webapp/static/json/orders.json")) {

            file.write(orderList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearCart(OrderDao order){
        order.getAll().clear();
    }
}