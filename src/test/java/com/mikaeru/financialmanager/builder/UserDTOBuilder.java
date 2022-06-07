package com.mikaeru.financialmanager.builder;

import com.github.javafaker.Faker;
import com.mikaeru.financialmanager.dto.UserRequest;

public class UserDTOBuilder {

    private String name;
    private String email;
    private String password;

    private static final Faker FAKER = new Faker();

    public UserDTOBuilder withName() {
        name = FAKER.name().fullName();
        return this;
    }

    public UserDTOBuilder withEmail() {
        email = FAKER.zelda().character().concat("@gmail.com");
        return this;
    }

    public UserDTOBuilder withExistingEmail() {
        email = "teste@gmail.com";
        return this;
    }

    public UserDTOBuilder withPassword() {
        password = FAKER.dragonBall().character().concat(String.valueOf(FAKER.number().randomNumber()));
        return this;
    }

    public UserRequest build() {
        return new UserRequest(name, email, password);
    }
}
