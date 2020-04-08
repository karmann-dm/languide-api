package com.karmanno.languagelearningappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.karmanno.languagelearningappapi.domain.Language;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Language language;
}
