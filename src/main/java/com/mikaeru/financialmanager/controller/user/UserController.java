package com.mikaeru.financialmanager.controller.user;

import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.service.user.UserService;
import com.mikaeru.financialmanager.domain.service.user.UserServiceImpl;
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

    private final UserService userService;

    public UserController(UserServiceImpl service) {
        this.userService = service;
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserRequest request) {
        User user = userService.saveUser(request.toModel());
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{externalId}")
                        .buildAndExpand(user.getExternalId())
                        .toUri()
        ).build();
    }
}
