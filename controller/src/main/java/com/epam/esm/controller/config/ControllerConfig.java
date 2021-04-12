package com.epam.esm.controller.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
@EnableWebMvc
public class ControllerConfig {

    private String localizationProperties = "classpath:localization/message";
    private String defaultEncoding = "UTF-8";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename(localizationProperties);
        source.setDefaultEncoding(defaultEncoding);
        return source;
    }

}