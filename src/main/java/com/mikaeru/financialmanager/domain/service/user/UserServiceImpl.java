package com.mikaeru.financialmanager.domain.service.user;

import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.repository.user.UserRepository;
import com.mikaeru.financialmanager.shared.exceptions.DuplicatedDataException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mikaeru.financialmanager.shared.exceptions.DomainException.Error.INVALID_DUPLICATED_DATA;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        Optional<User> userOptional = findUser(user.getEmail());

        if (userOptional.isPresent()) {
            throw new DuplicatedDataException(INVALID_DUPLICATED_DATA);
        }

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(String email) {
        return userRepository.findByEmail(email);
    }
}
