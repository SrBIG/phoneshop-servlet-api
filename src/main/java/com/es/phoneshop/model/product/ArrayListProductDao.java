package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();

    public static ArrayListProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    private List<Product> products;

    @Override
    synchronized public Product getProduct(Long id) throws ProductNotFoundException, IllegalArgumentException {
        checkId(id);
        return products
                .stream()
                .filter(product -> product.getId().equals(id))
                .filter(isValidProduct())
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    synchronized public List<Product> findProducts(String query, String sortBy, String order) {
        List<Product> actualProducts;

        if (query == null || query.trim().length() == 0) {
            actualProducts = findAllProducts();
        } else {
            actualProducts = findProductsByQuery(query);
        }

        if (sortBy == null || order == null) {
            return actualProducts;
        } else {
            return sort(actualProducts, sortBy, order);
        }
    }

    @Override
    synchronized public void save(Product product) throws IllegalArgumentException {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("product cant be null");
        }
        products.add(product);
    }

    @Override
    synchronized public void delete(Long id) throws IllegalArgumentException, ProductNotFoundException {
        checkId(id);
        if (!products.removeIf(product -> product.getId().equals(id))) {
            throw new ProductNotFoundException(id.toString());
        }
    }

    private Predicate<Product> isValidProduct() {
        return product -> null != product.getPrice() && product.getStock() > 0;
    }

    private void checkId(Long id) throws IllegalArgumentException {
        if (Objects.isNull(id) || id < 1) {
            throw new IllegalArgumentException("ID must be more 0");
        }
    }

    private List<Product> findAllProducts() {
        return products.stream()
                .filter(isValidProduct())
                .collect(Collectors.toList());
    }

    private List<Product> findProductsByQuery(String query) {
        Map<Product, Integer> productsByQuery = new HashMap<>();
        String[] queries = query.split("\\s");

        products.forEach(product -> {
            int actualLvl = 0;
            for (String curQuery : queries) {
                if (product.getDescription().toLowerCase().contains(curQuery.toLowerCase())) {
                    actualLvl++;
                }
            }
            if (actualLvl > 0) {
                productsByQuery.put(product, actualLvl);
            }
        });

        return productsByQuery
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .filter(isValidProduct())
                .collect(Collectors.toList());
    }

    private List<Product> sort(List<Product> actualProducts, String sortBy, String order) {
        if (sortBy.equals("description") && order.equals("asc")) {
            return actualProducts.stream()
                    .sorted(Comparator.comparing(Product::getDescription))
                    .collect(Collectors.toList());
        } else if (sortBy.equals("description") && order.equals("desc")) {
            return actualProducts.stream()
                    .sorted(Comparator.comparing(Product::getDescription).reversed())
                    .collect(Collectors.toList());
        } else if (sortBy.equals("price") && order.equals("asc")) {
            return actualProducts.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        } else if (sortBy.equals("price") && order.equals("desc")) {
            return actualProducts.stream()
                    .sorted(Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        } else return actualProducts;
    }
}
