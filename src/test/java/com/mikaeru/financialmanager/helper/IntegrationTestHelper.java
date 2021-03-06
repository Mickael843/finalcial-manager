package com.mikaeru.financialmanager.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/fixture/dataset.sql")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AUTO_CONFIGURED)
public abstract class IntegrationTestHelper extends TestHelper {

    @Autowired
    protected MockMvc mockMvc;

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
