package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dto.LoginInfo;
import com.karmanno.languagelearningappapi.entity.User;

public interface AuthService {
    User signUp(String username, String password, Language language);
    LoginInfo logIn(String username, String password);
}
