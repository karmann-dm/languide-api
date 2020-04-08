package com.karmanno.languagelearningappapi.service.processor;

import com.karmanno.languagelearningappapi.domain.ElementType;
import com.karmanno.languagelearningappapi.domain.Field;
import com.karmanno.languagelearningappapi.domain.FilledField;
import com.karmanno.languagelearningappapi.entity.Literal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardProcessor implements ElementProcessor {
    @Override
    public boolean acceptableFor(ElementType elementType) {
        return ElementType.CARDS.equals(elementType);
    }

    @Override
    public List<FilledField> process(Field field, List<Literal> literals) {
        Map<String, Object> options = field.getOptions();
        int cardsCount = Integer.parseInt(options.getOrDefault("cardsCount", 5).toString());
        int optionsCount = Integer.parseInt(options.getOrDefault("optionsCount", 4).toString());
        int perception = Integer.parseInt(options.getOrDefault("perceptionForCard", 10).toString());
        List<FilledField> filledFields = new ArrayList<>();
        Set<Literal> chosenLiterals = new LinkedHashSet<>();
        Random random = new Random();

        // create set of words for the exercise
        while (chosenLiterals.size() <= cardsCount) {
            int index = random.nextInt(literals.size());
            chosenLiterals.add(literals.get(index));
        }

        chosenLiterals.forEach(literal -> {
            Map<String, Object> values = new HashMap<>();
            values.put("word", literal.getWord());
            values.put("options", chooseOptions(literals, literal, optionsCount));
            values.put("correct", literal.getTranslation());
            FilledField filledField = new FilledField()
                    .setElementType(ElementType.CARDS)
                    .setValues(values);
            filledFields.add(filledField);
        });

        return filledFields;
    }

    private List<Literal> chooseOptions(List<Literal> literals, Literal except, int count) {
        Set<Literal> ls = new HashSet<>(literals);
        ls.remove(except);
        List<Literal> options = ls.parallelStream()
                .limit(count - 1)
                .collect(Collectors.toList());
        options.add(except);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomSwap = random.nextInt(options.size());

        Literal t = options.get(options.size() - 1);
        options.set(options.size() - 1, options.get(randomSwap));
        options.set(randomSwap, t);
        return options;
    }
}
