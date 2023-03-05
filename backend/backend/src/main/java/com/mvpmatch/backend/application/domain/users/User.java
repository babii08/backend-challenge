package com.mvpmatch.backend.application.domain.users;

import com.mvpmatch.backend.adapter.database.user.Role;
import com.mvpmatch.backend.application.domain.CoinsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    private Long id;
    private String userName;
    private String password;
    private List<CoinsType> deposit;
    private Set<Role> roles;
    private String token;

}
