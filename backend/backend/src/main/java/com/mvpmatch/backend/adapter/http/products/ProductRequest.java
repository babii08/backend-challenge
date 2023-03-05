package com.mvpmatch.backend.adapter.http.products;

import lombok.Data;

public record ProductRequest(String productName, int cost, int amountAvailable) {
}
