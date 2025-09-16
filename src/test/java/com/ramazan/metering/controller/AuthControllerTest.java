package com.ramazan.metering.controller;

import com.ramazan.metering.properties.KeycloakProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private KeycloakProperties props;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
    }

    @Test
    @DisplayName("Должен вернуть access_token при валидных кредах")
    void shouldReturnAccessToken_whenCredentialsValid() throws Exception {
        when(props.getUrl()).thenReturn("http://fake-keycloak");
        when(props.getRealm()).thenReturn("myrealm");
        when(props.getGrantType()).thenReturn("password");
        when(props.getClientId()).thenReturn("client123");
        when(props.getClientSecret()).thenReturn("secret123");

        String tokenResponse = """
                {
                  "access_token":"abc123",
                  "refresh_token":"ref123",
                  "expires_in":300,
                  "token_type":"Bearer"
                }
                """;

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(tokenResponse));

        mockMvc.perform(post("/api/v1/auth/token")
                        .param("username", "ramazan")
                        .param("password", "pwd123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("abc123"))
                .andExpect(jsonPath("$.refresh_token").value("ref123"))
                .andExpect(jsonPath("$.token_type").value("Bearer"));
    }

    @Test
    @DisplayName("Должен вернуть 400 если Keycloak вернул Bad Request")
    void shouldReturnBadRequest_whenKeycloakReturns400() throws Exception {
        when(props.getUrl()).thenReturn("http://fake-keycloak");
        when(props.getRealm()).thenReturn("myrealm");
        when(props.getGrantType()).thenReturn("password");
        when(props.getClientId()).thenReturn("client123");
        when(props.getClientSecret()).thenReturn("secret123");

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid"));

        mockMvc.perform(post("/api/v1/auth/token")
                        .param("username", "wrong")
                        .param("password", "wrong")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest());
    }
}
