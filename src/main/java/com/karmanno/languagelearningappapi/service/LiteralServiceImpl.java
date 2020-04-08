package com.karmanno.languagelearningappapi.service;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.LiteralType;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.gateway.YandexTranslatorGateway;
import com.karmanno.languagelearningappapi.repository.LiteralRepository;
import com.karmanno.languagelearningappapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LiteralServiceImpl implements LiteralService {
    private final LiteralRepository literalRepository;
    private final UserRepository userRepository;
    private final YandexTranslatorGateway yandexTranslatorGateway;

    @Override
    public Literal add(Integer userId, Language source, Language dest, String word, String translation) {
        if (translation == null)
            translation = yandexTranslatorGateway.translate(source, dest, word);
        return literalRepository.save(
                new Literal()
                    .setType(LiteralType.WORD)
                    .setPerception(0)
                    .setSource(source)
                    .setDestination(dest)
                    .setWord(word)
                    .setTranslation(translation)
                    .setUser(userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("No user with id " + userId)))
        );
    }

    @Override
    public List<Literal> getAll(Integer userId, String lang) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user with id " + userId));
        Language language = Language.valueOf(lang);
        List<Literal> sourceLiterals = literalRepository.findAllByUserAndSource(user, language);
        sourceLiterals.addAll(
                literalRepository.findAllByUserAndDestination(user, language)
                    .stream()
                    .map(literal -> {
                        Language lSource = literal.getSource();
                        Language lDest = literal.getDestination();
                        String lWord = literal.getWord();
                        String lTranslation = literal.getTranslation();
                        return literal.setSource(lDest)
                                .setDestination(lSource)
                                .setWord(lTranslation)
                                .setTranslation(lWord);
                    })
                    .collect(Collectors.toList())
        );
        return sourceLiterals;
    }
}
