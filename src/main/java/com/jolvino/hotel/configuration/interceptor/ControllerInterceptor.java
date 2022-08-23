package com.jolvino.hotel.configuration.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ControllerInterceptor implements HandlerInterceptor {

    public static final String CORRELATION_ID = "correlationID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationID = Optional.ofNullable(
                        request.getHeader("x-correlation-id"))
                .orElse(UUID.randomUUID().toString());
        MDC.put(CORRELATION_ID, correlationID);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());

        log.info("The operation returned HTTP Status: {} ", httpStatus);

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        MDC.remove(CORRELATION_ID);
    }
}
