package com.karmanno.languagelearningappapi.controller.literal;

import com.karmanno.languagelearningappapi.controller.IntegrationTest;
import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dsl.Given;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllLiteralsTest extends IntegrationTest {
    private User user;

    @Autowired
    private LiteralRepository literalRepository;

    @BeforeEach
    public void setUp() {
        user = Given.user(authService)
                .withUsername("user")
                .withPassword("pass")
                .please();
    }

    @DisplayName("Should get all literals correctly")
    @Test
    public void positive() throws Exception {
        List<Literal> literals = Given
                .literal(literalRepository)
                .withLiteral(user, Language.EN, Language.DE, "abc", "cde")
                .withLiteral(user, Language.EN, Language.DE, "cer", "ret")
                .withLiteral(user, Language.DE, Language.EN, "prt", "rpt")
                .please();

        performAuthorizedGet(
                "/literal",
                new AuthRequest()
                    .setUsername(user.getName())
                    .setPassword("pass")
        )
                .andExpect(
                        status().is(200)
                )
                .andExpect(
                        jsonPath("$.success").value(true)
                )
                .andExpect(
                        jsonPath("$.payload").isArray()
                )
                .andExpect(
                        jsonPath("$.payload[0].source").value("EN")
                );
    }
}
