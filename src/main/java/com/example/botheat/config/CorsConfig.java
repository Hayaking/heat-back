package com.example.botheat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域
 * @author haya
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping( "/**" ).allowedOrigins( "*" )
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowCredentials( true ).maxAge( 3600 );
    }
}
