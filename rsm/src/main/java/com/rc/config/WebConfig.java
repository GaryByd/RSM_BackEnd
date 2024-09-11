package com.rc.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 允许发送凭证（例如 Cookies 或 Authorization 头）
        config.addAllowedOriginPattern("*"); // 允许任意域名。Spring Boot 2.4+ 支持该方法，替代 `addAllowedOrigin`
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addAllowedMethod("*"); // 允许所有请求方法（GET, POST, etc.）
        config.setMaxAge(3600L); // 预检请求的缓存时间

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
