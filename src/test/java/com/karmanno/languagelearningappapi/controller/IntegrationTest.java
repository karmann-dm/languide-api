package com.karmanno.languagelearningappapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.dto.LoginInfo;
import com.karmanno.languagelearningappapi.service.AuthService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
@FlywayTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class IntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AuthService authService;

    protected WireMockServer wireMockServer = new WireMockServer(
            9090
    );

    @BeforeEach
    public void setUpIntegTest() {
        wireMockServer.start();
    }

    @AfterEach
    public void tearDownIntegTest() {
        wireMockServer.stop();
    }

    public ResultActions performUnauthorizedJsonPostWithBody(
            String url,
            Object payload
    ) throws Exception {
        return mockMvc
                .perform(
                        post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload))
                );
    }

    public ResultActions performAuthorizedJsonPostWithBody(
            String url,
            Object payload,
            AuthRequest authRequest
    ) throws Exception {
        LoginInfo info = authService.logIn(authRequest.getUsername(), authRequest.getPassword());
        return mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + info.getToken())
                    .content(objectMapper.writeValueAsString(payload))
        );
    }

    public ResultActions performAuthorizedGet(
            String url,
            AuthRequest authRequest
    ) throws Exception {
        LoginInfo info = authService.logIn(authRequest.getUsername(), authRequest.getPassword());
        return mockMvc.perform(
                get(url)
                    .header("Authorization", "Bearer " + info.getToken())
        );
    }
}
