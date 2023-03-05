package com.mvpmatch.backend.application.domain.products;

public interface ProductRepository {

    Product saveProduct(Product product);

    Product findProduct(Long productId);

    Product removeProduct(Long productId);
}
