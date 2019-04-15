package com.es.phoneshop.model.order.dao.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private Cart cart;
    @Mock
    private BigDecimal cartPrice;

    @Mock
    private Order order;
    @Mock
    private BigDecimal orderPrice;
    private DeliveryMode deliveryMode = DeliveryMode.COURIER;
    private BigDecimal deliveryCost = deliveryMode.getCost();

    private OrderService orderService;

    @Before
    public void setup(){
        orderService = OrderServiceImpl.getInstance();

        when(cart.getTotalPrice()).thenReturn(cartPrice);

        when(order.getTotalPrice()).thenReturn(orderPrice);
        when(order.getDeliveryMode()).thenReturn(deliveryMode);
    }

    @Test
    public void testCreateOrder() {
        orderService.createOrder(cart);
    }

    @Test
    public void testPlaceOrder() {
        BigDecimal totalPrice = mock(BigDecimal.class);
        when(orderPrice.add(deliveryCost)).thenReturn(totalPrice);
        orderService.placeOrder(order);
        verify(order).setTotalPrice(totalPrice);
    }
}
