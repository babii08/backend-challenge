package com.mvpmatch.backend.adapter.http.products;


import com.mvpmatch.backend.application.domain.products.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ProductService {

    ProductRepresentation getProduct(final Long productId);

    ProductRepresentation addProduct(final ProductRequest productRequest, final String sellerName);

    ProductRepresentation updateProduct(Long productId, final ProductRequest productRequest, final String sellerName);

    ProductRepresentation removeProduct(Long productId, final String sellerName);

}
