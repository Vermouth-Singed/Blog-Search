package com.search.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blog-search")
                .pathsToMatch("/search/blog/**")
                .build();
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("블로그 검색 API")
                        .description("블로그 검색 API")
                        .version("v0.0.1"));
    }
}
