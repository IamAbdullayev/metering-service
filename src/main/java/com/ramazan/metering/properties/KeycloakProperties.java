package com.ramazan.metering.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String url;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String grantType = "password";
}
