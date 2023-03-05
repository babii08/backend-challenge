package com.mvpmatch.backend.application.domain.purchase;

import com.mvpmatch.backend.application.domain.CoinsType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    private Long productId;
    private String productName;
    private int productAmount;
    private int amountSpent;
    private List<Integer> change;

}
