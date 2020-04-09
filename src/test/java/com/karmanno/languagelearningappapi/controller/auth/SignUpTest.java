package com.karmanno.languagelearningappapi.controller.auth;

import com.karmanno.languagelearningappapi.controller.IntegrationTest;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.dto.SignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SignUpTest extends IntegrationTest {
    @DisplayName("Should sign up correctly")
    @Test
    public void positive() throws Exception {
        performUnauthorizedJsonPostWithBody(
                "/auth/signup",
                new SignupRequest()
                    .setUsername("username")
                    .setPassword("password")
                    .setLanguage("DE")
        )
                .andExpect(
                        status().is(200)
                )
                .andExpect(
                        jsonPath("$.success").value(true)
                )
                .andExpect(
                        jsonPath("$.payload.id").isNumber()
                )
                .andExpect(
                        jsonPath("$.payload.name").value("username")
                );
    }
}
