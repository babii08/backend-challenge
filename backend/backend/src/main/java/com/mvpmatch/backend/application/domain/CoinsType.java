package com.mvpmatch.backend.application.domain;

import com.mvpmatch.backend.application.domain.exceptions.InvalidAmountException;
import lombok.Getter;

import java.util.Arrays;

public enum CoinsType {

    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);

    @Getter
    private final int value;
    private static final int ACCEPTED_MULTIPLE = 5;

    CoinsType(final int value) {
        this.value = value;
    }

    public static boolean isAcceptedInput(int coin) {
        return Arrays.stream(values())
                .map(val -> val.value)
                .anyMatch(val -> val == coin);
    }

    public static CoinsType getCoinType(int coin) {
        return Arrays.stream(values())
                .filter(value -> value.getValue() == coin)
                .findFirst()
                .orElseThrow(() -> new InvalidAmountException("Invalid value for introduced coin. Only coins "
                        + Arrays.toString(values()) + " are allowed"));
    }
}
