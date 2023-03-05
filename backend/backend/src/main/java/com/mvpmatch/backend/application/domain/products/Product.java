package com.mvpmatch.backend.application.domain.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long productId;
    private int amountAvailable;
    private int cost;
    private String productName;
    private Long sellerId;

}
