package com.mvpmatch.backend.adapter.http.purchase;

import com.mvpmatch.backend.application.domain.purchase.Purchase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")
@AllArgsConstructor
public class PurchaseResource {

    private PurchaseService purchaseFacade;
    private ModelMapper modelMapper;

    @PostMapping("/{userId}/deposit")
    ResponseEntity<AmountAvailableResponse> depositCoins(final @PathVariable @NotNull Long userId,
                                                         final @RequestParam(value = "coinValue") int coinValue,
                                                         final UriComponentsBuilder uriComponentsBuilder) {
        final var totalAmountAvailable = purchaseFacade.depositCoin(coinValue, userId);
        final var uri = uriComponentsBuilder
                .path("/amount/{userId}")
                .buildAndExpand(userId)
                .toUri();
        return ResponseEntity
                .created(uri)
                .body(new AmountAvailableResponse(totalAmountAvailable));
    }

    @GetMapping("/{userId}/amount")
    ResponseEntity<AmountAvailableResponse> getAmount(@PathVariable @NotNull Long userId) {
        final var totalAmountAvailable = purchaseFacade.getAmountAvailable(userId);
        return ResponseEntity.ok(new AmountAvailableResponse(totalAmountAvailable));
    }

    @PostMapping("/{userId}/buy")
    ResponseEntity<PurchaseResponse> buyProduct(@PathVariable @NotNull Long userId,
                                                @RequestBody @NotNull PurchaseRequest purchaseRequest) {
        final var purchaseReq = modelMapper.map(purchaseRequest, Purchase.class);
        final var purchase = purchaseFacade.buyProduct(purchaseReq, userId);
        final var purchaseResponse = new PurchaseResponse(
                purchase.getProductId(), purchase.getProductName(), purchase.getProductAmount(),
                purchase.getAmountSpent(), purchase.getChange()
        );
        return ResponseEntity.ok(purchaseResponse);
    }

    @PostMapping("/{userId}/reset")
    ResponseEntity<List<Integer>> buyProduct(@PathVariable @NotNull Long userId) {
        final var amount = purchaseFacade.resetAmount(userId);
        return ResponseEntity.ok(amount);
    }
}
