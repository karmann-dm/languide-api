package com.karmanno.languagelearningappapi.dsl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.karmanno.languagelearningappapi.domain.Language;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class YandexTranslatorBuilder {
    private Language sourceLanguage;
    private Language destLanguage;
    private WireMockServer wireMockServer;
    private static final String EN_TRANSLATION = "impeccable";
    private static final String DE_TRANSLATION = "einwandfrei";

    public YandexTranslatorBuilder(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    public YandexTranslatorBuilder withSource(Language source) {
        this.sourceLanguage = source;
        return this;
    }

    public YandexTranslatorBuilder withDest(Language dest) {
        this.destLanguage = dest;
        return this;
    }

    public void please() {
        String destWord;
        if (sourceLanguage.equals(Language.DE)) {
            destWord = EN_TRANSLATION;
        } else {
            destWord = DE_TRANSLATION;
        }
        String lang = (sourceLanguage.name() + "-" + destLanguage.name()).toLowerCase();
        String body = "{\"code\":200,\"lang\":\"" + lang + "\",\"text\":[\"" + destWord + "\"]}";

        wireMockServer.stubFor(
                post(
                        urlPathEqualTo("/api/v1.5/tr.json/translate")
                ).willReturn(
                        aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(body)
                )
        );
    }
}
