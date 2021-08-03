package com.mlab.assessment.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final Environment env;

    private final String SECURITY_TYPE = "BearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_TYPE))
                .components(getSecurityComponents());
//                .info(getApiInfo());
    }

    private Components getSecurityComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_TYPE, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

    private Info getApiInfo(){
        return new Info()
                .title(env.getProperty("api.title"))
                .version(env.getProperty("api.version"))
                .description(env.getProperty("api.description"))
                .contact(getContactInfo())
                .termsOfService(env.getProperty("api.tnc-url"))
                .license(new License()
                        .name(env.getProperty("api.license.name"))
                        .url(env.getProperty("api.license.url")));
    }

    private Contact getContactInfo(){
        return new Contact()
                .name(env.getProperty("api.contact.name"))
                .email(env.getProperty("api.contact.email"))
                .url(env.getProperty("api.contact.url"));
    }
}
