package com.cmc.suppin.global.security.jwt;

import com.cmc.suppin.global.security.exception.SecurityErrorCode;
import com.cmc.suppin.global.security.util.HttpResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        HttpResponseUtil.writeErrorResponse(response, SecurityErrorCode.FORBIDDEN);
    }
}
