package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.epam.esm.entity.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final int BCRYPT_ROUND = 12;
    public static final String URL_FOR_ANTMATCHERS = "/v2/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers(HttpMethod.GET, URL_FOR_ANTMATCHERS)
                .hasAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers(HttpMethod.POST, URL_FOR_ANTMATCHERS)
                .hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, URL_FOR_ANTMATCHERS)
                .hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, URL_FOR_ANTMATCHERS)
                .hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.name())
                .build(), User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Role.USER.name())
                .build()

        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_ROUND);
    }
}
