package com.url.shortener.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket urlShortenerAPI() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.url.shortener"))
                .paths(regex(".*")).build().apiInfo(apiInfo());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("URL Shortener").description("APIs needed for operations pertaining to URL shortening")
                .license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .contact(new Contact("Dileep Kumar Jami", "", "dileepkumarjami@gmail.com"))
                .termsOfServiceUrl("").version("1.0.0").build();
    }
}
