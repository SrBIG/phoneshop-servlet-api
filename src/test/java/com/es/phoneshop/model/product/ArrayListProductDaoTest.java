package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArrayListProductDaoTest {
    private static ProductDao productDao;

    private static Product realProduct;
    private static Long realId = 1L;
    private static BigDecimal realProductPrice = new BigDecimal(1000);
    private static Integer realProductStock = 1;
    private static String realProductDescription = "Real Product";

    private static Product notValidProduct;
    private static Long notValidProductId = 3L;
    private static BigDecimal notValidProductPrice = null;
    private static Integer notValidProductStock = 0;

    private static Product product1;
    private static Long product1Id = 2L;
    private static BigDecimal product1Price = new BigDecimal(1);
    private static Integer product1Stock = 1;
    private static String product1Description = "Product #1";

    private Long failId = 999L;

    private String nullQuery = null;
    private String nullSotBy = null;
    private String nullOrder = null;
    private String queryForRealProduct = "Real";
    private String sortByDescription = "description";
    private String sortByPrice = "price";
    private String ascOrder = "asc";
    private String descOrder = "desc";

    @BeforeClass
    public static void setup() {
        productDao = ArrayListProductDao.getInstance();

        realProduct = mock(Product.class);
        when(realProduct.getId()).thenReturn(realId);
        when(realProduct.getPrice()).thenReturn(realProductPrice);
        when(realProduct.getStock()).thenReturn(realProductStock);
        when(realProduct.getDescription()).thenReturn(realProductDescription);
        productDao.save(realProduct);

        product1 = mock(Product.class);
        when(product1.getId()).thenReturn(product1Id);
        when(product1.getPrice()).thenReturn(product1Price);
        when(product1.getStock()).thenReturn(product1Stock);
        when(product1.getDescription()).thenReturn(product1Description);
        productDao.save(product1);

        notValidProduct = mock(Product.class);
        when(notValidProduct.getId()).thenReturn(notValidProductId);
        when(notValidProduct.getPrice()).thenReturn(notValidProductPrice);
        when(notValidProduct.getStock()).thenReturn(notValidProductStock);
        productDao.save(notValidProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByFailId() throws ProductNotFoundException {
        productDao.getProduct(failId);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByInvalidId() throws ProductNotFoundException {
        productDao.getProduct(0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetNotValidProduct() throws ProductNotFoundException {
        productDao.getProduct(notValidProductId);
    }

    @Test
    public void testSuccessfulExecutionGetProduct() throws ProductNotFoundException {
        Product expected = realProduct;
        Product actual = productDao.getProduct(realId);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllValidProducts() {
        List<Product> expected = new ArrayList<>();
        expected.add(realProduct);
        expected.add(product1);
        List<Product> actual = productDao.findProducts(nullQuery, nullSotBy, nullOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsByQuery() {
        List<Product> expected = new ArrayList<>();
        expected.add(realProduct);
        List<Product> actual = productDao.findProducts(queryForRealProduct, nullSotBy, nullOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsWithAscOrderByDescription() {
        List<Product> expected = new ArrayList<>();
        expected.add(product1);
        expected.add(realProduct);
        List<Product> actual = productDao.findProducts(nullQuery, sortByDescription, ascOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsWithDescOrderByDescription() {
        List<Product> expected = new ArrayList<>();
        expected.add(realProduct);
        expected.add(product1);
        List<Product> actual = productDao.findProducts(nullQuery, sortByDescription, descOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsWithAscOrderByPrice() {
        List<Product> expected = new ArrayList<>();
        expected.add(product1);
        expected.add(realProduct);
        List<Product> actual = productDao.findProducts(nullQuery, sortByPrice, ascOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsWithDescOrderByPrice() {
        List<Product> expected = new ArrayList<>();
        expected.add(realProduct);
        expected.add(product1);
        List<Product> actual = productDao.findProducts(nullQuery, sortByPrice, descOrder);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindProductsWithWrongSortParameters() {
        List<Product> expected = new ArrayList<>();
        expected.add(realProduct);
        expected.add(product1);
        List<Product> actual = productDao.findProducts(nullQuery, descOrder, sortByPrice);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullProduct() {
        productDao.save(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProductByInvalidId() throws ProductNotFoundException {
        productDao.getProduct(0);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProductNoResult() throws ProductNotFoundException {
        productDao.delete(failId);
    }

    @Test
    public void testSuccessfulExecutionDeleteProduct() throws ProductNotFoundException {
        productDao.delete(realId);
        productDao.delete(product1Id);
        productDao.delete(notValidProductId);
        assertTrue(productDao.findProducts(nullQuery, nullSotBy, nullOrder).isEmpty());
        returnDeletedProductsToProductDao();
    }

    private void returnDeletedProductsToProductDao() {
        productDao.save(realProduct);
        productDao.save(product1);
        productDao.save(notValidProduct);
    }
}
