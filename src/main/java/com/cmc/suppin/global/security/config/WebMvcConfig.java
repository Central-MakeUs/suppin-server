package com.cmc.suppin.global.security.config;

import com.cmc.suppin.global.security.reslover.CurrentAccountArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final CurrentAccountArgumentResolver currentAccountArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentAccountArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(getAllowOrigins())
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true);
    }

    private String[] getAllowOrigins() {
        return Arrays.asList(
                "http://localhost:3000",
                "https://localhost:3000",
                "https://dev.suppin.store",
                "https://suppin.store",
                "http://192.168.0.100:3000" // 모바일 디바이스의 IP 주소
        ).toArray(String[]::new);
    }
}
