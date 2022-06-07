package com.mikaeru.financialmanager.domain.service.user;

import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUser(String email) {
        return userRepository.findByEmail(email);
    }
}
