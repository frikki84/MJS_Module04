package com.epam.esm.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class SpringSecurityExceptionHandler implements AuthenticationEntryPoint {
    private String contentType = "application/json";
    private String messageTitle = "{ \"error\": \"";


    private final String realmName = "CustomRealm";

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        resolver.resolveException(request, response, null, authException);
    }
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//            AuthenticationException authenticationException) throws IOException, ServletException {

//        response.setContentType(contentType);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().println(messageTitle + authenticationException.getMessage() + "\" }");



    }

