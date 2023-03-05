package com.mvpmatch.backend.adapter.http.purchase;

import com.mvpmatch.backend.application.domain.CoinsType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coin {

    private Long coinId;
    private CoinsType coinsType;
    private int amount;

}
