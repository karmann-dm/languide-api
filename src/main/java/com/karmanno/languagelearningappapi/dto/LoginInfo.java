package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class LoginInfo {
    Integer id;
    String username;
    String language;
    String token;
}
