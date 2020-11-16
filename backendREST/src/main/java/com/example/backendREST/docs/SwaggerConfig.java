package com.example.backendREST.docs;

import java.beans.BeanInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RequestMethod;

import com.example.backendREST.auth.ApplicationUser;
import com.google.common.net.HttpHeaders;


import io.swagger.models.auth.In;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.example.backendREST.controller"))
	                .paths(PathSelectors.any())
	                .build()
	                .useDefaultResponseMessages(false)
	                .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
	                .securityContexts(Arrays.asList(securityContext()));
        
                /*.globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                		.name("Authorization")
                		.description("Bearer token")
                		.modelRef(new ModelRef("string"))
                		.parameterType("header")
                		.required(true)
                		.build()));*/
                		           		
                
    }

	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("ADMIN", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(
	        new SecurityReference("Token Access", authorizationScopes));
	}
	
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.ant("/pais/**"))
	        .forPaths(PathSelectors.ant("/usuario/**"))
	        .build();
	}
	
	

}