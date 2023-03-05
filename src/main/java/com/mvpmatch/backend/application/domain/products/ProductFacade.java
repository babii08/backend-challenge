package com.mvpmatch.backend.application.domain.products;

import com.mvpmatch.backend.adapter.http.products.ProductRepresentation;
import com.mvpmatch.backend.adapter.http.products.ProductRequest;
import com.mvpmatch.backend.adapter.http.products.ProductService;
import com.mvpmatch.backend.adapter.http.users.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductFacade implements ProductService {

    private ProductServiceImp productService;
    private UserService userService;
    private ModelMapper modelMapper;

    public ProductRepresentation getProduct(final Long productId) {
        log.info("Getting product with id {}", productId);
        final var product = productService.getProduct(productId);
        return new ProductRepresentation(
                product.getProductId(), product.getProductName(),
                product.getCost(), product.getSellerId(), product.getAmountAvailable()
        );
    }

    public ProductRepresentation addProduct(final ProductRequest productRequest, final String sellerName) {
        log.info("Adding new product: {}, from seller with id: {}", productRequest.productName(), sellerName);
        final var seller = userService.findUserByName(sellerName);
        final var product = modelMapper.map(productRequest, Product.class);
        product.setSellerId(seller.getId());
        final var savedProduct = productService.addProduct(product);
        return new ProductRepresentation(
                savedProduct.getProductId(), savedProduct.getProductName(),
                savedProduct.getCost(), savedProduct.getSellerId(), savedProduct.getAmountAvailable()
        );
    }

    public ProductRepresentation updateProduct(Long productId, final ProductRequest productRequest, final String sellerName) {
        log.info("Updating product with id: {}. Product request is: {}", productId, productRequest.toString());
        final var seller = userService.findUserByName(sellerName);
        final var product = modelMapper.map(productRequest, Product.class);
        product.setProductId(productId);
        final var updatedProduct = productService.updateProduct(product, seller.getId());
        return new ProductRepresentation(
                updatedProduct.getProductId(), updatedProduct.getProductName(),
                updatedProduct.getCost(), updatedProduct.getSellerId(), updatedProduct.getAmountAvailable()
        );
    }

    public ProductRepresentation removeProduct(Long productId, final String sellerName) {
        log.info("Removing product with id: {}", productId);
        final var seller = userService.findUserByName(sellerName);
        final var updatedProduct = productService.removeProduct(productId, seller.getId());
        return new ProductRepresentation(
                updatedProduct.getProductId(), updatedProduct.getProductName(),
                updatedProduct.getCost(), updatedProduct.getSellerId(), updatedProduct.getAmountAvailable()
        );
    }
}
