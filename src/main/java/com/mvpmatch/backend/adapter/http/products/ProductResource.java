package com.mvpmatch.backend.adapter.http.products;

import com.mvpmatch.backend.application.domain.products.ProductServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductResource {

    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductRepresentation> getProduct(@PathVariable @NotNull Long productId) {
        final var product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductRepresentation> addProduct(@RequestBody ProductRequest productRequest,
                                                            UriComponentsBuilder uriComponentsBuilder) {
        final var sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
        final var newProduct = productService.addProduct(productRequest, sellerName);
        final var uri = uriComponentsBuilder
                .path("/{productId}")
                .buildAndExpand(newProduct.productId())
                .toUri();
        return ResponseEntity
                .created(uri)
                .body(newProduct);
    }

    @PutMapping("/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductRepresentation> updateProduct(@PathVariable Long productId,
                                                               @RequestBody ProductRequest productRequest) {
        final var sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
        final var updatedProduct = productService.updateProduct(productId, productRequest, sellerName);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ProductRepresentation> removeProduct(Long productId) {
        final var sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
        final var removedProduct = productService.removeProduct(productId, sellerName);
        return ResponseEntity.ok(removedProduct);
    }

}
