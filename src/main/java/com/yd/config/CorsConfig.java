package com.yd.config;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决客户端跨越问题
 * @author: YD
 */
@Configuration
@ComponentScan("com.yd")
public class CorsConfig implements WebMvcConfigurer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CorsConfig.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

}
