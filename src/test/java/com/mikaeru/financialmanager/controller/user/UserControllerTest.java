package com.mikaeru.financialmanager.controller.user;

import com.mikaeru.financialmanager.builder.UserDTOBuilder;
import com.mikaeru.financialmanager.dto.UserRequest;
import com.mikaeru.financialmanager.helper.IntegrationTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTestHelper {

    private static final String ENDPOINT = "/v1/users/";

    @Test
    @DisplayName("Ao fornecer um usuário valido, deve retornar status code 201.")
    void GIVEN_ValidUser_MUST_ReturnCreatedStatusCode() throws Exception {
        UserRequest request = new UserDTOBuilder().withName().withEmail().withPassword().build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**" + ENDPOINT + "*"));
    }

    @Test
    @DisplayName("Dado um usuário já existente, deve retornar status code 400.")
    void GIVEN_ExistingUser_MUST_ReturnDuplicatedDataException() throws Exception {
        UserRequest request = new UserDTOBuilder().withName().withExistingEmail().withPassword().build();

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                        .content(asJsonString(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("Invalid duplicated data!")));
    }
}
