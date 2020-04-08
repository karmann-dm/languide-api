package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
