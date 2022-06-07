package com.mikaeru.financialmanager.controller.user;

import com.mikaeru.financialmanager.builder.UserDTOBuilder;
import com.mikaeru.financialmanager.dto.UserRequest;
import com.mikaeru.financialmanager.helper.IntegrationTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTestHelper {

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
}
