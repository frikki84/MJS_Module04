package com.epam.esm.service.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.epam.esm.service.exception.JwtAuthenticationException;
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final SpringSecurityExceptionHandler exceptionHandler;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, SpringSecurityExceptionHandler exceptionHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.exceptionHandler = exceptionHandler;

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (Objects.nonNull(token) && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (Objects.nonNull(authentication)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            exceptionHandler.commence((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse,
                    new JwtAuthenticationException(LocalizationExceptionMessageValues.JWT_EXCEPTION.getMessage()));

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
