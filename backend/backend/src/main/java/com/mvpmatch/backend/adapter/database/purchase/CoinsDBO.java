package com.mvpmatch.backend.adapter.database.purchase;

import com.mvpmatch.backend.application.domain.CoinsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinsDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coinId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "coins_type")
    private CoinsType coinsType;

    @Column(name = "amount")
    private int amount;

}
