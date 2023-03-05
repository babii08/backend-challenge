package com.mvpmatch.backend.adapter.http.purchase;

import com.mvpmatch.backend.application.domain.purchase.Purchase;
import java.util.List;

public interface PurchaseService {

    int depositCoin(int coinValue, Long userId);

    Purchase buyProduct(final Purchase purchaseReq, final Long userId);

    int getAmountAvailable(Long userId);

    List<Integer> resetAmount(Long userId);

}
