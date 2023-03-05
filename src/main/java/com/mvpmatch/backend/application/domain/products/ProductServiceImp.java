package com.mvpmatch.backend.application.domain.products;

import com.mvpmatch.backend.application.domain.exceptions.InvalidAmountException;
import com.mvpmatch.backend.application.domain.exceptions.InvalidCostException;
import com.mvpmatch.backend.application.domain.exceptions.ProductChangingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImp {

    private ProductRepository productRepository;

    public static final int COST_MULTIPLE = 5;

    public Product addProduct(final Product product) {
        validateAmount(product.getAmountAvailable());
        validateCost(product.getCost());
        return productRepository.saveProduct(product);
    }

    public Product updateProduct(final Product product, Long sellerId) {
        validateAmount(product.getAmountAvailable());
        validateCost(product.getCost());
        validateSeller(product.getProductId(), sellerId);
        product.setSellerId(sellerId);
        return productRepository.saveProduct(product);
    }

    public Product removeProduct(Long productId, Long sellerId) {
        validateSeller(productId, sellerId);
        return productRepository.removeProduct(productId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findProduct(productId);
    }

    private void validateSeller(final Long productId, final Long sellerId) {
        final var existingProduct = productRepository.findProduct(productId);
        if (!existingProduct.getSellerId().equals(sellerId)) {
            throw new ProductChangingException("Product with seller id " + existingProduct.getSellerId() +
                    " cannot be changed by seller with id " + sellerId);
        }
    }

    private void validateAmount(int amountAvailable) {
        if (amountAvailable < 1) {
            throw new InvalidAmountException("Invalid amount. You should add at least one product.");
        }
    }

    private void validateCost(int cost) {
        if (cost == 0 || cost % COST_MULTIPLE != 0) {
            throw new InvalidCostException("Cost should be a multiple of " + COST_MULTIPLE);
        }
    }
}
