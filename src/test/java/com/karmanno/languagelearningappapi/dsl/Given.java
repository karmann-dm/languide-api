package com.karmanno.languagelearningappapi.dsl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.karmanno.languagelearningappapi.repository.ExerciseRepository;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;
import com.karmanno.languagelearningappapi.service.AuthService;

public class Given {
    public static UserBuilder user(AuthService authService) {
        return new UserBuilder(authService);
    }

    public static YandexTranslatorBuilder translator(WireMockServer wireMockServer) {
        return new YandexTranslatorBuilder(wireMockServer);
    }

    public static LiteralBuilder literal(LiteralRepository literalRepository) {
        return new LiteralBuilder(literalRepository);
    }

    public static ExerciseBuilder exercise(ExerciseRepository exerciseRepository) {
        return new ExerciseBuilder(exerciseRepository);
    }
}
