package com.mikaeru.financialmanager.dto;

import com.mikaeru.financialmanager.domain.model.user.User;

public record UserRequest(
        String name,
        String email,
        String password
) {

    public User toModel() {
        return new User(this.name, this.email, this.password);
    }
}
