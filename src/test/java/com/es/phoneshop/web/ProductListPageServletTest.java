package com.es.phoneshop.web;

import com.es.phoneshop.web.page.ProductListPageServlet;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductListPageServletTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static RequestDispatcher requestDispatcher;
    private static ServletConfig servletConfig;
    private static ProductListPageServletForTest servlet;

    @BeforeClass
    public static void setup() {
        servlet = new ProductListPageServletForTest();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        servletConfig = mock(ServletConfig.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.init(servletConfig);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    private static class ProductListPageServletForTest extends ProductListPageServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            super.doGet(request, response);
        }
    }
}