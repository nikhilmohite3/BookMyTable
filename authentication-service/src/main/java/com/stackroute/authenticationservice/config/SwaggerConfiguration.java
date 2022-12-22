package com.stackroute.authenticationservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Authenticate Service").apiInfo(apiInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/login.*")).build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Authenticate Service")
                .description("API For Generating Token")
                .termsOfServiceUrl("")
                .license("License")
                .licenseUrl("").version("1.0").build();
    }
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
    }

    private List<SecurityReference> sf() {

        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }
}
