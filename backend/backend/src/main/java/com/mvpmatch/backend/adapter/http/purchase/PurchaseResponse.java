package com.mvpmatch.backend.adapter.http.purchase;

import com.mvpmatch.backend.application.domain.CoinsType;

import java.util.List;

public record PurchaseResponse(Long productId, String productName, int productAmount, int amountSpent, List<Integer> change) {

}
