package com.karmanno.languagelearningappapi.controller.literal;

import com.karmanno.languagelearningappapi.controller.IntegrationTest;
import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dsl.Given;
import com.karmanno.languagelearningappapi.dto.AddLiteralRequest;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AddLiteralTest extends IntegrationTest {
    @DisplayName("Should add literal correctly")
    @Test
    public void positiveAddLiteral() throws Exception {
        Given.translator(wireMockServer)
                .withSource(Language.EN)
                .withDest(Language.DE)
                .please();
        User user = Given.user(authService)
                .withUsername("user")
                .withPassword("pass")
                .please();

        performAuthorizedJsonPostWithBody(
                "/literal",
                new AddLiteralRequest()
                    .setSourceLang("EN")
                    .setDestLang("DE")
                    .setWord("impeccable"),
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
                        jsonPath("$.payload.source").value("EN")
                )
                .andExpect(
                        jsonPath("$.payload.destination").value("DE")
                )
                .andExpect(
                        jsonPath("$.payload.perception").value(0)
                )
                .andExpect(
                        jsonPath("$.payload.translation").value("einwandfrei")
                )
                .andExpect(
                        jsonPath("$.payload.id").isNumber()
                )
                .andExpect(
                        jsonPath("$.payload.type").value("WORD")
                )
                .andExpect(
                        jsonPath("$.payload.user.id").value(user.getId())
                );
    }

    @DisplayName("Should add reverse literal correctly")
    @Test
    public void positiveAddReverseLiteral() throws Exception {
        Given.translator(wireMockServer)
                .withSource(Language.DE)
                .withDest(Language.EN)
                .please();
        User user = Given.user(authService)
                .withUsername("user")
                .withPassword("pass")
                .please();

        performAuthorizedJsonPostWithBody(
                "/literal",
                new AddLiteralRequest()
                        .setSourceLang("DE")
                        .setDestLang("EN")
                        .setWord("einwandfrei"),
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
                        jsonPath("$.payload.source").value("DE")
                )
                .andExpect(
                        jsonPath("$.payload.destination").value("EN")
                )
                .andExpect(
                        jsonPath("$.payload.perception").value(0)
                )
                .andExpect(
                        jsonPath("$.payload.translation").value("impeccable")
                )
                .andExpect(
                        jsonPath("$.payload.id").isNumber()
                )
                .andExpect(
                        jsonPath("$.payload.type").value("WORD")
                )
                .andExpect(
                        jsonPath("$.payload.user.id").value(user.getId())
                );
    }
}
