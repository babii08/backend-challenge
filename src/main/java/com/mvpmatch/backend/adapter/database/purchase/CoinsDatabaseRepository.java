package com.mvpmatch.backend.adapter.database.purchase;

import com.mvpmatch.backend.application.domain.CoinsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinsDatabaseRepository extends JpaRepository<CoinsDBO, Long> {

    CoinsDBO findByCoinsType(CoinsType coinsType);

}
