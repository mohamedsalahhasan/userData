package com.employees.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Employees Data APIs")
                        .description("Employees-Service all APIs detailed documentation")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }}
