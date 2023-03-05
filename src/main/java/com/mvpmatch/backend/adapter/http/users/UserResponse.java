package com.mvpmatch.backend.adapter.http.users;

public record UserResponse(Long id, String userName, String jwtToken) { }
