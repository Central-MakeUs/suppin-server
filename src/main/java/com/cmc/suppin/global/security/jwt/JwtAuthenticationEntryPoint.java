package com.cmc.suppin.global.security.jwt;

import com.cmc.suppin.global.security.exception.SecurityErrorCode;
import com.cmc.suppin.global.security.util.HttpResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        HttpResponseUtil.writeErrorResponse(response, SecurityErrorCode.UNAUTHORIZED);
    }
}
