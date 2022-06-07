package com.mikaeru.financialmanager.domain.service.user;

import com.mikaeru.financialmanager.builder.UserDTOBuilder;
import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.repository.user.UserRepository;
import com.mikaeru.financialmanager.helper.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest extends TestHelper {

    private UserService service;
    private static final String FAIL = "Não deveria passar por aqui.";

    @BeforeEach
    void setUp() {
        UserRepository repository = mock(UserRepository.class);
        service = new UserService(repository);

        User user = new UserDTOBuilder().withName().withExistingEmail().withPassword().build().toModel();

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Dado um email existente, deve retornar o usuário no banco de dados!")
    void GIVEN_ExistingEmail_MUST_ReturnUserSavedInDatabase() {
        User user = new UserDTOBuilder().withName().withExistingEmail().withPassword().build().toModel();

        Optional<User> userOptional = service.findUser(user.getEmail());

        if (userOptional.isEmpty()) {
            fail(FAIL);
        }

        User userSavedInDatabase = userOptional.get();

        assertNotNull(userSavedInDatabase.getCreatedAt());
        assertThat(userSavedInDatabase.getEmail(), equalTo(user.getEmail()));
        assertThat(userSavedInDatabase.getExternalId(), not(equalTo(user.getExternalId())));
    }

    @Test
    @DisplayName("Dado um email inexistente, deve retornar um objeto vazio")
    void GIVEN_NonexistentEmail_MUST_ReturnOptionalEmpty() {
        User user = new UserDTOBuilder().withName().withEmail().withPassword().build().toModel();

        Optional<User> userOptional = service.findUser(user.getEmail());

        assertTrue(userOptional.isEmpty());
    }
}
