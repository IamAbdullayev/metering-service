package com.ramazan.metering.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = SwaggerConfig.KEYCLOAK_AUTH_SCHEME_NAME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = SwaggerConfig.BEARER_FORMAT,
        scheme = SwaggerConfig.SCHEME
)
public class SwaggerConfig {
    public static final String KEYCLOAK_AUTH_SCHEME_NAME = "KeycloakAuth";
    public static final String SCHEME = "bearer";
    public static final String BEARER_FORMAT = "JWT";

    public static final String API_GROUP = "Metering-service";
    public static final String API_PACKAGE_TO_SCAN = "com.ramazan.metering";
    public static final String API_TITLE = "Metering Service API";
    public static final String API_DESCRIPTION = "API for managing meters and meter readings (secured via Keycloak)";
    public static final String API_VERSION = "v1.0";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(API_GROUP)
                .packagesToScan(API_PACKAGE_TO_SCAN)
                .build();
    }

    @Bean
    public OpenAPI meteringOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION))
                .addSecurityItem(new SecurityRequirement().addList(KEYCLOAK_AUTH_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(KEYCLOAK_AUTH_SCHEME_NAME,
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .name(KEYCLOAK_AUTH_SCHEME_NAME)
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme(SCHEME)
                                        .bearerFormat(BEARER_FORMAT)
                        ))
                .externalDocs(new ExternalDocumentation());
    }
}
