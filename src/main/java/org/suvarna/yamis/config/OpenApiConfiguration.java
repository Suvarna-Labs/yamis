package org.suvarna.yamis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "YAMIS Base - Identity & Access Server API",
                version = "1.0.0",
                description = "REST APIs for Yet Another Modular Identity Server (User, Group, Role, and Privilege Management).",
                contact = @Contact(
                        name = "Priyabrata Nayak",
                        email = "workpns120@gmail.com"
                )
        ),
        security = @SecurityRequirement(name = "ApiKeyAuth")
)
@SecurityScheme(
        name = "ApiKeyAuth",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-KEY",
        description = "Header-based API Key authentication (e.g., 'X-API-KEY: your_api_key')"
)
public class OpenApiConfiguration {
}

