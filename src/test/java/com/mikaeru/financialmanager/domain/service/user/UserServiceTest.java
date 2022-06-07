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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest extends TestHelper {

    private UserService service;

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

        User userSavedInDatabase = null;
        User user = new UserDTOBuilder().withName().withExistingEmail().withPassword().build().toModel();

        try {
            userSavedInDatabase = service.findUser(user.getEmail());
        } catch (Exception e) {
            fail("Não deve lançar uma exceção");
        }

        assertNotNull(userSavedInDatabase.getCreatedAt());
        assertThat(userSavedInDatabase.getEmail(), equalTo(user.getEmail()));
        assertThat(userSavedInDatabase.getExternalId(), not(equalTo(user.getExternalId())));
    }

    @Test
    @DisplayName("Dado um email inexistente, deve lançar uma exceção do tipo UserNotFoundException!")
    void GIVEN_NonexistentEmail_MUST_ThrowUserNotFoundException() {
        User user = new UserDTOBuilder().withName().withEmail().withPassword().build().toModel();

        try {
            service.findUser(user.getEmail());
            fail("Não deve chegar até aqui");
        } catch (Exception e) {
            // TODO Após criar tratamento de exceções, alterar esse bloco.
            System.out.println("CHEGUEI ATÉ AQUI");
        }
    }
}
