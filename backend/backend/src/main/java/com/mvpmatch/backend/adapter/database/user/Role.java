package com.mvpmatch.backend.adapter.database.user;


import lombok.Getter;
import org.webjars.NotFoundException;

import java.util.Arrays;

import static com.mvpmatch.backend.application.domain.Constants.*;

public enum Role {

    SELLER(SELLER_ROLE),
    BUYER(BUYER_ROLE),
    ADMIN(ADMIN_ROLE);

    @Getter
    public final String role;

    Role(final String role) {
        this.role = role;
    }

    public static Role getRole(String role) {
        return Arrays.stream(values())
                .filter(value -> value.role.equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No role defined for: " + role));
    }
}

