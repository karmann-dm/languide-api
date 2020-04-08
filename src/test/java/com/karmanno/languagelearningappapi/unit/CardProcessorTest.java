package com.karmanno.languagelearningappapi.unit;

import com.karmanno.languagelearningappapi.domain.ElementType;
import com.karmanno.languagelearningappapi.domain.Field;
import com.karmanno.languagelearningappapi.domain.FilledField;
import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.service.processor.CardProcessor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CardProcessorTest {
    private CardProcessor cardProcessor = new CardProcessor();

    @Test
    public void testCorrectConverters() {
        Field field = new Field();
        field.setType("CARD");
        field.setOptions(new HashMap<String, Object>() {{
            put("cardsCount", 5);
            put("optionsCount", 4);
            put("perceptionForCard", 10);
        }});

        List<Literal> literals = new ArrayList<Literal>() {{
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_1").setTranslation("translation_1"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_2").setTranslation("translation_2"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_3").setTranslation("translation_3"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_4").setTranslation("translation_4"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_5").setTranslation("translation_5"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_6").setTranslation("translation_6"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_7").setTranslation("translation_7"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_8").setTranslation("translation_8"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_9").setTranslation("translation_9"));
            add(new Literal().setSource(Language.DE).setDestination(Language.EN).setWord("word_10").setTranslation("translation_10"));
        }};

        List<FilledField> filledFields = cardProcessor.process(field, literals);
        filledFields.forEach(filledField -> {
            assertEquals(ElementType.CARDS, filledField.getElementType());
            Map<String, Object> values = filledField.getValues();
            String correctIndex = values.get("correct").toString().split("_")[1];
            String wordIndex = values.get("word").toString().split("_")[1];
            assertEquals(correctIndex, wordIndex);
            List<Literal> option = (List<Literal>) values.get("options");
            List<String> optionInds = option.stream()
                    .map(o -> o.getWord().split("_")[1])
                    .collect(Collectors.toList());
            assertTrue(optionInds.contains(wordIndex));
            assertEquals(optionInds.indexOf(wordIndex), optionInds.lastIndexOf(wordIndex));
        });
    }
}
