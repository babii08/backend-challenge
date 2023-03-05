package com.mvpmatch.backend.adapter.http.users;

import com.mvpmatch.backend.adapter.database.user.Role;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequest(@NotNull String userName, @NotNull String password, Set<Role> roles) {
}
