package com.mvpmatch.backend.adapter.http.purchase;

public record PurchaseRequest(Long productId, int productAmount) {
}
