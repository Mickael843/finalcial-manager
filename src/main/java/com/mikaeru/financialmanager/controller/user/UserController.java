package com.mikaeru.financialmanager.controller.user;

import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.repository.user.UserRepository;
import com.mikaeru.financialmanager.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserRequest request) {

        User user = request.toModel();

        repository.save(user);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{externalId}")
                        .buildAndExpand(user.getExternalId())
                        .toUri()
        ).build();
    }
}
