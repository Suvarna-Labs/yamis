package org.suvarna.yamis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public OpenApiCustomizer pageableOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getPaths() != null) {
                openApi.getPaths().values().forEach(pathItem ->
                        pathItem.readOperations().forEach(operation -> {
                            if (operation.getParameters() != null) {
                                for (Parameter parameter : operation.getParameters()) {
                                    if ("page".equalsIgnoreCase(parameter.getName())) {
                                        parameter.setDescription("Page number (1-based index)");
                                        parameter.setExample(1);
                                        if (parameter.getSchema() != null) {
                                            parameter.getSchema().setDefault(1);
                                            parameter.getSchema().setExample(1);
                                        }
                                    } else if ("size".equalsIgnoreCase(parameter.getName())) {
                                        parameter.setDescription("Number of records per page");
                                        parameter.setExample(10);
                                        if (parameter.getSchema() != null) {
                                            parameter.getSchema().setDefault(10);
                                            parameter.getSchema().setExample(10);
                                        }
                                    }
                                }
                            }
                        })
                );
            }
        };
    }
}
