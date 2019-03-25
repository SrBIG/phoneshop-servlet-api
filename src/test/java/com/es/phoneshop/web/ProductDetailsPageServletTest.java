package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import com.es.phoneshop.web.page.ProductDetailsPageServlet;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductDetailsPageServletTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static RequestDispatcher requestDispatcher;
    private static ServletConfig servletConfig;
    private static ProductDetailsPageServletForTest servlet;
    private static ArrayListProductDao productDaoTest;
    private static String pathInfo = "/1";

    @BeforeClass
    public static void setup() {
        servlet = new ProductDetailsPageServletForTest();

        productDaoTest = mock(ArrayListProductDao.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        servletConfig = mock(ServletConfig.class);

        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        servlet.init(servletConfig);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testProductNotFound() throws IOException, ProductNotFoundException {
        when(productDaoTest.getProduct(anyLong())).thenThrow(new ProductNotFoundException());
        String expected = ProductNotFoundException.class.toString().substring(6);
        try {
            servlet.doGet(request, response);
            fail("Expected ServletException.");
        } catch (ServletException actual){
            assertEquals(actual.getMessage(), expected);
        }
    }

    private static class ProductDetailsPageServletForTest extends ProductDetailsPageServlet {

        @Override
        public void init(ServletConfig config) {
            productDao = productDaoTest;
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            super.doGet(request, response);
        }
    }
}
