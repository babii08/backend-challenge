package com.mvpmatch.backend.adapter.http.products;

public record ProductRepresentation(Long productId, String productName, int cost, long userId, int amount) {}
