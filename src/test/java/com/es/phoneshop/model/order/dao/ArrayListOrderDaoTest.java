package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.dao.exception.OrderNotFoundException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ArrayListOrderDaoTest {
    private static Order realOrder;
    private static Long realId = 1L;
    private static String realSecureId = "realOrder-secure-id";

    private static Order order1;
    private static Long order1Id = 2L;
    private static String order1SecureId = "order1-secure-id";

    private Long failId = 999L;
    private String failSecureId = "fail-secure-id";

    private static OrderDao orderDao;

    @BeforeClass
    public static void setup() {
        orderDao = ArrayListOrderDao.getInstance();

        realOrder = mock(Order.class);
        when(realOrder.getId()).thenReturn(realId);
        when(realOrder.getSecureId()).thenReturn(realSecureId);
        orderDao.save(realOrder);

        order1 = mock(Order.class);
        when(order1.getId()).thenReturn(order1Id);
        when(order1.getSecureId()).thenReturn(order1SecureId);
        orderDao.save(order1);
    }

    @AfterClass
    public static void destroyClass() throws OrderNotFoundException {
        orderDao.delete(realId);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetOrderByFailId() throws OrderNotFoundException {
        orderDao.getOrder(failId);
    }

    @Test
    public void testGetOrderByIdSuccessfully() throws OrderNotFoundException {
        orderDao.getOrder(realId);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetOrderByFailSecureId() throws OrderNotFoundException {
        orderDao.getBySecureId(failSecureId);
    }

    @Test
    public void testGetOrderBySecureIdSuccessfully() throws OrderNotFoundException {
        orderDao.getBySecureId(realSecureId);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testDeleteOrderNoResult() throws OrderNotFoundException {
        orderDao.delete(failId);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testSuccessfulExecutionDeleteOrder() throws OrderNotFoundException {
        orderDao.delete(order1Id);
        orderDao.getOrder(order1Id);
    }

    @Test
    public void testSaveOrderSuccessfully() throws OrderNotFoundException {
        Order orderToSave = mock(Order.class);
        orderDao.save(orderToSave);
        verify(orderToSave).setId(anyLong());
        verify(orderToSave).setSecureId(anyString());
    }
}
