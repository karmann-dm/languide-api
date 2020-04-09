package com.karmanno.languagelearningappapi.controller.auth;

import com.karmanno.languagelearningappapi.controller.IntegrationTest;
import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dsl.Given;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.dto.SignupRequest;
import com.karmanno.languagelearningappapi.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogInTest extends IntegrationTest {
    @Autowired
    private AuthService authService;

    @DisplayName("Test correct logging in")
    @Test
    public void positive() throws Exception {
        Given.user(authService)
                .withUsername("user")
                .withPassword("password")
                .withLanguage(Language.DE)
                .please();

        performUnauthorizedJsonPostWithBody(
                "/auth/login",
                new SignupRequest()
                    .setUsername("user")
                    .setPassword("password")
                    .setLanguage("DE")
        ).andExpect(
                jsonPath("$.success").value(true)
        ).andExpect(
                jsonPath("$.errorMessage").value(nullValue())
        ).andExpect(
                jsonPath("$.payload.token").isNotEmpty()
        );
    }

    @DisplayName("Test incorrect logging in with incorrect username")
    @Test
    public void negativeIncorrectUsername() throws Exception {
        Given.user(authService)
                .withUsername("user")
                .withPassword("password")
                .withLanguage(Language.DE)
                .please();

        performUnauthorizedJsonPostWithBody(
                "/auth/login",
                new AuthRequest()
                    .setUsername("user")
                    .setPassword("pass")
        ).andExpect(
                status().is(401)
        );
    }

    @DisplayName("Test incorrect logging in with incorrect password")
    @Test
    public void negativeIncorrectPassword() throws Exception {
        Given.user(authService)
                .withUsername("user")
                .withPassword("password")
                .withLanguage(Language.DE)
                .please();

        performUnauthorizedJsonPostWithBody(
                "/auth/login",
                new AuthRequest()
                        .setUsername("user1")
                        .setPassword("password")
        ).andExpect(
                status().is(401)
        );
    }
}
