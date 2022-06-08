package com.mikaeru.financialmanager.domain.service.user;

import com.mikaeru.financialmanager.builder.UserDTOBuilder;
import com.mikaeru.financialmanager.domain.model.user.User;
import com.mikaeru.financialmanager.domain.repository.user.UserRepository;
import com.mikaeru.financialmanager.helper.TestHelper;
import com.mikaeru.financialmanager.shared.exceptions.DuplicatedDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mikaeru.financialmanager.shared.exceptions.DomainException.Error.INVALID_DUPLICATED_DATA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest extends TestHelper {

    private UserService service;
    private UserRepository repository;

    private UserDTOBuilder userBuilder;

    private static final String FAIL = "Não deveria passar por aqui.";

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);

        userBuilder = new UserDTOBuilder().withName().withPassword();
    }

    @Test
    @DisplayName("Dado um email existente, deve retornar o usuário no banco de dados!")
    void GIVEN_ExistingEmail_MUST_ReturnUserSavedInDatabase() {
        User userSaved = userBuilder.withExistingEmail().build().toModel();
        when(repository.findByEmail(userSaved.getEmail())).thenReturn(Optional.of(userSaved));

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

    @Test
    @DisplayName("Dado um usuário valido, deve salva-lo no banco de dados")
    void GIVEN_ValidUser_MUST_SaveUserInDatabase() {
        User userSavedInDatabase = null;
        User user = userBuilder.withEmail().build().toModel();

        when(repository.save(any())).thenReturn(user);

        try {
            userSavedInDatabase = service.saveUser(user);
        } catch (Exception e) {
            fail(FAIL);
        }

        assertThat(userSavedInDatabase.getName(), equalTo(user.getName()));
        assertThat(userSavedInDatabase.getEmail(), equalTo(user.getEmail()));
        assertThat(userSavedInDatabase.getPassword(), equalTo(user.getPassword()));
        assertThat(userSavedInDatabase.getExternalId(), equalTo(user.getExternalId()));
    }

    @Test
    @DisplayName("Dado um usuário valido, deve lançar uma exceção do tipo DuplicatedDataException")
    void GIVEN_ExistingUser_MUST_ThrowDuplicatedDataException() {
        User user = userBuilder.withEmail().build().toModel();

        when(repository.save(any())).thenReturn(user);
        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        try {
            service.saveUser(user);
            fail(FAIL);
        } catch (DuplicatedDataException e) {
            assertThat(e.getMessage(), equalTo(INVALID_DUPLICATED_DATA.getMessage()));
        }
    }
}
