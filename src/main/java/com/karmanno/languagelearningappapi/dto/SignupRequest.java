package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String language;
}
