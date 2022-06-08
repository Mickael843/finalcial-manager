package com.mikaeru.financialmanager.domain.service.user;

import com.mikaeru.financialmanager.domain.model.user.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findUser(String email);
}
