package com.mlab.assessment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Configuration
public class OpenApiConfiguration {

    private final String SECURITY_TYPE = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_TYPE))
                .components(getSecurityComponents());
    }

    private Components getSecurityComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_TYPE, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }
}
