package com.karmanno.languagelearningappapi.dsl;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.LiteralType;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;

import java.util.ArrayList;
import java.util.List;

public class LiteralBuilder {
    private final LiteralRepository literalRepository;
    private List<Literal> literals = new ArrayList<>();

    public LiteralBuilder(LiteralRepository literalRepository) {
        this.literalRepository = literalRepository;
    }

    public LiteralBuilder withLiteral(
            User user,
            Language source, Language dest,
            String sourceWord, String destWord
    ) {
        literals.add(
                literalRepository.save(
                        new Literal()
                            .setType(LiteralType.WORD)
                            .setUser(user)
                            .setPerception(0)
                            .setSource(source)
                            .setWord(sourceWord)
                            .setDestination(dest)
                            .setTranslation(destWord)
                )
        );
        return this;
    }

    public LiteralBuilder withLiteral(
            User user,
            Language source, Language dest,
            String sourceWord, String destWord,
            Integer perception
    ) {
        literals.add(
                literalRepository.save(
                        new Literal()
                                .setType(LiteralType.WORD)
                                .setUser(user)
                                .setPerception(perception)
                                .setSource(source)
                                .setWord(sourceWord)
                                .setDestination(dest)
                                .setTranslation(destWord)
                )
        );
        return this;
    }

    public List<Literal> please() {
        return literals;
    }
}
