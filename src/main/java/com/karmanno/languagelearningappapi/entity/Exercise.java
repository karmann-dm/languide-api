package com.karmanno.languagelearningappapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private Integer version;

    private String template;
}
