package com.epam.esm.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityExceptionHandler implements AuthenticationEntryPoint {
    private String contentType = "application/json";
    private String messageTitle = "{ \"Error message\": \"";
    private String messageTail = "\" }";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {

        response.setContentType(contentType);
        response.getOutputStream().println(messageTitle + authenticationException.getMessage() + messageTail);

    }

}