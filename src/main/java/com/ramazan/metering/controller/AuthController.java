package com.ramazan.metering.controller;

import com.ramazan.metering.properties.KeycloakProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API", description = "Endpoints for obtaining access tokens via Keycloak")
public class AuthController {

    private final RestTemplate restTemplate;
    private final KeycloakProperties props;

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

        String url = String.format("%s/realms/%s/protocol/openid-connect/token",
                props.getUrl(), props.getRealm());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", props.getGrantType());
        body.add("client_id", props.getClientId());
        body.add("client_secret", props.getClientSecret());
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, request, String.class);
    }
}
