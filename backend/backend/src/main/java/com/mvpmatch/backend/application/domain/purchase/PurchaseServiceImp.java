package com.mvpmatch.backend.application.domain.purchase;

import com.mvpmatch.backend.adapter.database.user.Role;
import com.mvpmatch.backend.adapter.http.purchase.PurchaseService;
import com.mvpmatch.backend.adapter.http.users.UserService;
import com.mvpmatch.backend.application.domain.CoinsType;
import com.mvpmatch.backend.application.domain.exceptions.InvalidAmountException;
import com.mvpmatch.backend.application.domain.exceptions.InvalidRoleException;
import com.mvpmatch.backend.application.domain.products.Product;
import com.mvpmatch.backend.application.domain.products.ProductServiceImp;
import com.mvpmatch.backend.application.domain.users.User;
import com.mvpmatch.backend.application.domain.users.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mvpmatch.backend.application.domain.CoinsType.getCoinType;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class PurchaseServiceImp implements PurchaseService {

    private ProductServiceImp productService;
    private UserService userService;
    private UserRepository userRepository;
    private PurchaseRepository purchaseRepository;

    @Transactional
    public int depositCoin(int coinValue, Long userId) {
        final var user = this.getBuyerUser(userId);
        CoinsType coinType = getCoinType(coinValue);
        purchaseRepository.addCoin(coinType);
        user.getDeposit().add(coinType);
        userRepository.saveUser(user);
        return calculateAmountAvailable(user);
    }

    @Transactional
    public Purchase buyProduct(final Purchase purchaseReq, final Long userId) {
        final var user = getBuyerUser(userId);
        final var product = productService.getProduct(purchaseReq.getProductId());
        final var usersAmountAvailable = calculateAmountAvailable(user);
        validateEnoughAmount(product, purchaseReq, usersAmountAvailable);
        final var purchase = purchaseProduct(usersAmountAvailable, product, purchaseReq.getProductAmount());
        product.setAmountAvailable(product.getAmountAvailable() - purchaseReq.getProductAmount());
        productService.updateProduct(product, product.getSellerId());
        user.setDeposit(Collections.emptyList());
        userRepository.saveUser(user);
        return purchase;
    }

    private Purchase purchaseProduct(final Integer usersAmountAvailable,
                                     final Product product,
                                     final int amountToPurchase) {
        final var amountToSpend = product.getCost() * amountToPurchase;
        final var change = usersAmountAvailable - amountToSpend;
        final var changeList = calculateChangeWithGreedy(change);
        return Purchase.builder()
                .productId(product.getProductId())
                .amountSpent(amountToSpend)
                .change(changeList)
                .productName(product.getProductName())
                .productAmount(amountToPurchase)
                .build();
    }

    private List<Integer> calculateChangeWithGreedy(int change) {
        final var coins = purchaseRepository.getCoins();
        List<Integer> changeList = new ArrayList<>();
        coins.sort(((coin1, coin2) -> coin2.getCoinsType().getValue()-coin1.getCoinsType().getValue()));
        int index = 0;
        while(change > 0 && index < coins.size()) {
            final var currentCoin = coins.get(index);
            final var currentCoinsType = currentCoin.getCoinsType();
            if (change < currentCoinsType.getValue()) {
                index++;
                continue;
            }
            if (currentCoin.getAmount() == 0) {
                index++;
                continue;
            }
            change -= currentCoinsType.getValue();
            changeList.add(currentCoinsType.getValue());
            currentCoin.setAmount(currentCoin.getAmount() - 1);
        }
        return changeList;
    }

    private void validateEnoughAmount(final Product product,
                                      final Purchase purchase,
                                      final Integer usersAmountAvailable) {
        if (product.getCost() * purchase.getProductAmount() > usersAmountAvailable) {
            throw new InvalidAmountException("No enough money to buy this product. Please deposit " +
                    (product.getCost() * purchase.getProductAmount() - usersAmountAvailable) +
                    " more cents to be able to purchase this product");
        }
    }

    public int getAmountAvailable(Long userId) {
        final var user = getBuyerUser(userId);
        return calculateAmountAvailable(user);
    }

    private User getBuyerUser(final Long userId) {
        final var user = userService.getUser(userId);
        if(!user.getRoles().contains(Role.BUYER)) {
            throw new InvalidRoleException("User is not a buyer. This operation is only supported for buyers");
        }
        return user;
    }

    private Integer calculateAmountAvailable(final User user) {
        return user.getDeposit().stream()
                .map(CoinsType::getValue)
                .reduce(0, Integer::sum);
    }

    @Transactional
    public List<Integer> resetAmount(Long userId) {
        final var user = getBuyerUser(userId);
        final var amount = calculateAmountAvailable(user);
        final var userCoins = calculateChangeWithGreedy(amount);
        user.setDeposit(Collections.emptyList());
        userRepository.saveUser(user);
        return userCoins;
    }
}
