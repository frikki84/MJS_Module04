package com.epam.esm.service.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter jwtTokenFilter;

    public JwtConfiguration(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        builder.addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);

    }
}
