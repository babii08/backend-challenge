package com.mvpmatch.backend.adapter.database.purchase;

import com.mvpmatch.backend.adapter.http.purchase.Coin;
import com.mvpmatch.backend.application.domain.CoinsType;
import com.mvpmatch.backend.application.domain.purchase.PurchaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PurchaseDatabaseRepository implements PurchaseRepository {

    private CoinsDatabaseRepository coinsDatabaseRepository;
    private ModelMapper modelMapper;

    @Override
    public void addCoin(CoinsType coinsType) {
//        final var coinDBO = coinsDatabaseRepository.findByCoinsType(coinsType);
        final var coinDBO = coinsDatabaseRepository.findById(1L).orElseThrow();
        coinDBO.setAmount(coinDBO.getAmount() + 1);
        coinsDatabaseRepository.save(coinDBO);
    }

    @Override
    public List<Coin> getCoins() {
        return coinsDatabaseRepository.findAll()
                .stream()
                .map(coin -> modelMapper.map(coin, Coin.class))
                .collect(Collectors.toList());
    }

}
