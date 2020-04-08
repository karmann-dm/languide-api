package com.karmanno.languagelearningappapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "labels")
public class Label {
    @Id
    private Integer id;
    private String name;
}
