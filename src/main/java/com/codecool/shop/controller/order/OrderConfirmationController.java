package com.codecool.shop.controller.order;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.controller.EmailController;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.OrderDetails;
import com.codecool.shop.model.OrderItem;

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


@WebServlet(urlPatterns = {"/order-confirmation"})
public class OrderConfirmationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDetails orderDetails = new OrderDetails();
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        orderDetails.setOrders(orderDataStore.getAll());
        double total = 0;
        Currency currency = null;
        for (OrderItem orderItem : orderDetails.getOrders()) {
            total += orderItem.subtotalPrice;
            currency = orderItem.getDefaultCurrency();
        }
        orderDetails.setTotal(total);
        orderDetails.setCurrency(currency);

        writeFileToJSON(orderDataStore);

        String email = (String) req.getSession().getAttribute("user_email");
        EmailController.sendEmail(email, orderDetails, currency);

        context.setVariable("orderDetails", orderDetails);
        engine.process("order-confirmation.html", context, resp.getWriter());
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