package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException, IllegalArgumentException {
        checkId(id);
        return products
                .stream()
                .filter(product -> product.getId().equals(id))
                .filter(isValidProduct())
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }

    @Override
    synchronized public List<Product> findProducts() {
        return products.stream()
                .filter(isValidProduct())
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) throws IllegalArgumentException {
        if (Objects.isNull(product)){
            throw new IllegalArgumentException("product cant be null");
        }
        products.add(product);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException, ProductNotFoundException {
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
}
