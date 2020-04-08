package com.karmanno.languagelearningappapi.service.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karmanno.languagelearningappapi.domain.ElementType;
import com.karmanno.languagelearningappapi.domain.FilledTemplate;
import com.karmanno.languagelearningappapi.domain.Template;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.User;
import com.karmanno.languagelearningappapi.service.LiteralService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateProcessor {
    private final List<ElementProcessor> elementProcessors;
    private final ObjectMapper objectMapper;
    private final LiteralService literalService;

    @SneakyThrows
    public String process(String input, User user) {
        Template template = objectMapper.readValue(input, Template.class);
        FilledTemplate filledTemplate = new FilledTemplate();
        List<Literal> literals = literalService.getAll(user.getId(), user.getLanguage().name());
        template.getFields().forEach(field -> {
            ElementType elementType = ElementType.valueOf(field.getType().toUpperCase());
            ElementProcessor elementProcessor = elementProcessors.stream()
                    .filter(p -> p.acceptableFor(elementType))
                    .findFirst()
                    .orElseThrow();
            filledTemplate.getFields().addAll(elementProcessor.process(field, literals));
        });
        return objectMapper.writeValueAsString(filledTemplate);
    }
}
