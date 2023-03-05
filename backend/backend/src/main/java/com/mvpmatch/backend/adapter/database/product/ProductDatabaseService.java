package com.mvpmatch.backend.adapter.database.product;

import com.mvpmatch.backend.application.domain.products.Product;
import com.mvpmatch.backend.application.domain.products.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
@Slf4j
public class ProductDatabaseService implements ProductRepository {

    private ProductDatabaseRepository productRepository;
    private ModelMapper modelMapper;

    @Override
    public Product saveProduct(final Product product) {
        var productDBO = modelMapper.map(product, ProductDBO.class);
        productDBO = productRepository.save(productDBO);
        return modelMapper.map(productDBO, Product.class);
    }

    @Override
    public Product findProduct(final Long productId) {
        final var productDBO = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("No product found for id: " + productId));
        return modelMapper.map(productDBO, Product.class);
    }

    @Override
    public Product removeProduct(final Long productId) {
        final var productDBO = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("No product found for id: " + productId));
        productRepository.deleteById(productId);
        return modelMapper.map(productDBO, Product.class);
    }

}
