package com.karmanno.languagelearningappapi.dsl;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.service.AuthService;

public class UserBuilder {
    private AuthService authService;
    private String username;
    private String password;
    private Language language;

    public UserBuilder(AuthService authService) {
        this.authService = authService;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withLanguage(Language language) {
        this.language = language;
        return this;
    }

    public User please() {
        return authService.signUp(username, password, language);
    }
}
