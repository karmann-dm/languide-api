package com.karmanno.languagelearningappapi.gateway;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dto.TranslationResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YandexTranslatorGateway {
    private final RestTemplate restTemplate;

    @Value("${app.yandexApiKey}")
    private String yandexApiKey;

    @Value("${app.yandexScheme}")
    private String yandexScheme;

    @Value("${app.yandexHost}")
    private String yandexHost;

    @Value("${app.yandexPort}")
    private Integer yandexPort;

    @SneakyThrows
    public String translate(Language source, Language dest, String word) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("key", yandexApiKey);
        map.add("text", word);
        map.add("lang", source.toString().toLowerCase() + "-" + dest.toString().toLowerCase());
        map.add("format", "plain");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(yandexScheme)
                .host(yandexHost)
                .path("/api/v1.5/tr.json/translate");
        if (yandexPort != null)
            builder.port(yandexPort);

        ResponseEntity<TranslationResponse> response = restTemplate.postForEntity(
                builder.build().toUri(), request , TranslationResponse.class
        );

        return Optional.ofNullable(response.getBody()).orElseThrow(() -> new RuntimeException("Empty body"))
                .getText().get(0);
    }
}
