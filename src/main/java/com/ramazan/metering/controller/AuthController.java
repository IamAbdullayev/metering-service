package com.ramazan.metering.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "Endpoints for obtaining access tokens via Keycloak")
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Operation(
            summary = "Get access token",
            description = "Obtain an access token from Keycloak using username and password (Direct Access Grants flow)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                      "access_token": "eyJhbGciOiJIUzI1...",
                                      "refresh_token": "eyJhbGciOiJIUzI1...",
                                      "expires_in": 300,
                                      "token_type": "Bearer"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid request or wrong credentials"),
            @ApiResponse(responseCode = "500", description = "Keycloak server error")
    })
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String username,
                                           @RequestParam String password) {
        String url = "http://localhost:8088/realms/meter-app/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", "meter-api");
        body.add("client_secret", "BROxD0mVsJuHZLETYWbPEpoxqtjXpbqA");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, request, String.class);
    }
}
