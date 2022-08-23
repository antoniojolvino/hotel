package com.jolvino.hotel.configuration;

import com.jolvino.hotel.configuration.interceptor.ControllerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Component
public class InterceptorsConfiguration implements WebMvcConfigurer {
    private final ControllerInterceptor inperceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inperceptor);
    }
}
