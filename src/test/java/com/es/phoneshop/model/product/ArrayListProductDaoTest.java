package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSuccessfulExecutionSaveAndFindProducts() {
        Product product = mock(Product.class);
        BigDecimal price = new BigDecimal(1);
        Integer stock = 1;

        when(product.getPrice()).thenReturn(price);
        when(product.getStock()).thenReturn(stock);

        productDao.save(product);

        List<Product> expected = new ArrayList<>();
        expected.add(product);
        List<Product> actual = productDao.findProducts();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsNotInStock() {
        Product product = mock(Product.class);
        BigDecimal price = new BigDecimal(1);
        Integer stock = 0;

        when(product.getPrice()).thenReturn(price);
        when(product.getStock()).thenReturn(stock);

        productDao.save(product);

        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsNullPrice() {
        Product product = mock(Product.class);
        BigDecimal price = null;
        Integer stock = 1;

        when(product.getPrice()).thenReturn(price);
        when(product.getStock()).thenReturn(stock);

        productDao.save(product);

        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductNoResult() {
        Long realId = 1L;
        Long failId = 10L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(realId);
        productDao.save(product);

        try {
            productDao.getProduct(failId);
            fail("Expected NoSuchElementException.");
        } catch (ProductNotFoundException expected) {
            assertEquals(expected.getMessage(), failId.toString());
        }
    }

    @Test
    public void testSuccessfulExecutionGetProduct() throws ProductNotFoundException {
        Long id = 1L;
        BigDecimal price = new BigDecimal(1);
        Integer stock = 1;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(id);
        when(product.getPrice()).thenReturn(price);
        when(product.getStock()).thenReturn(stock);

        productDao.save(product);

        Product expected = product;
        Product actual = productDao.getProduct(id);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteProductNoResult() {
        Long realId = 1L;
        Long failId = 10L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(realId);
        productDao.save(product);

        try {
            productDao.delete(failId);
            fail("Expected NoSuchElementException.");
        } catch (ProductNotFoundException expected) {
            assertEquals(expected.getMessage(), failId.toString());
        }
    }

    @Test
    public void testSuccessfulExecutionDeleteProduct() throws ProductNotFoundException {
        Long id = 1L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(id);

        productDao.save(product);
        productDao.delete(id);

        assertTrue(productDao.findProducts().isEmpty());
    }
}
