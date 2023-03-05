package com.mvpmatch.backend.application.domain.purchase;

import com.mvpmatch.backend.adapter.http.purchase.Coin;
import com.mvpmatch.backend.application.domain.CoinsType;

import java.util.List;

public interface PurchaseRepository {

    void addCoin(CoinsType coinsType);

    List<Coin> getCoins();
}
