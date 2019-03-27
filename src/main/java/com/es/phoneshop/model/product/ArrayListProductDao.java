package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

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

        if (sortBy == null || sortBy.trim().length() == 0) {
            return actualProducts;
        } else if (order == null || order.trim().length() == 0) {
            return sort(actualProducts, sortBy, "asc");
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
        if (!products.removeIf(product -> product.getId().equals(id))) {
            throw new ProductNotFoundException(id.toString());
        }
    }

    private Predicate<Product> isValidProduct() {
        return product -> null != product.getPrice() && product.getStock() > 0;
    }

    private List<Product> findAllProducts() {
        return products.stream()
                .filter(isValidProduct())
                .collect(Collectors.toList());
    }

    private List<Product> findProductsByQuery(String query) {
        Map<Product, Integer> productsByQuery = new HashMap<>();
        String[] queries = query.split("\\s+");

        products.stream()
                .filter(isValidProduct())
                .filter(product -> product.getDescription() != null)
                .forEach(product -> {
                    int actualLvl = (int) Arrays.stream(queries)
                            .filter(curQuery -> product.getDescription().toLowerCase().contains(curQuery.toLowerCase()))
                            .count();
                    if (actualLvl > 0) {
                        productsByQuery.put(product, actualLvl);
                    }
                });

        return productsByQuery
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Product> sort(List<Product> actualProducts, String sortBy, String order) {
        Comparator<Product> comparator;

        if (sortBy.equals("description") && order.equals("asc")) {
            comparator = Comparator.comparing(Product::getDescription);
        } else if (sortBy.equals("description") && order.equals("desc")) {
            comparator = Comparator.comparing(Product::getDescription).reversed();
        } else if (sortBy.equals("price") && order.equals("asc")) {
            comparator = Comparator.comparing(Product::getPrice);
        } else if (sortBy.equals("price") && order.equals("desc")) {
            comparator = Comparator.comparing(Product::getPrice).reversed();
        } else return actualProducts;

        return actualProducts.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
