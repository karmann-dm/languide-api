package com.karmanno.languagelearningappapi.entity;

import com.karmanno.languagelearningappapi.domain.Language;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "literals")
public class Literal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LiteralType type;

    @Enumerated(EnumType.STRING)
    private Language source;

    @Enumerated(EnumType.STRING)
    private Language destination;

    private Integer perception;

    private String word;

    private String translation;

    @ManyToOne(targetEntity = Label.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label label;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
