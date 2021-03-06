package com.es.phoneshop.web.page;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.dao.OrderDao;
import com.es.phoneshop.model.order.dao.exception.OrderNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = extractSecureId(request);
            Order order = orderDao.getBySecureId(id);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
        } catch (NumberFormatException | OrderNotFoundException exception) {
            response.sendError(404, "Order not found");
        }
    }

    private String extractSecureId(HttpServletRequest request) throws NumberFormatException {
        return request.getPathInfo().substring(1);
    }
}