package com.fererlab.config;

import com.fererlab.security.filter.PrincipalCredential;
import com.fererlab.security.filter.SecurityApiKeyAuthoritiesDelegate;
import com.fererlab.security.filter.SecurityApiKeyCredentialsDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("SecurityApiKeyFilter")
    private Filter securityApiKeyFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(securityApiKeyFilter, BasicAuthenticationFilter.class)
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public SecurityApiKeyAuthoritiesDelegate getSecurityApiKeyAuthoritiesDelegate() {
        return apiKey -> Stream.of("ROLE_NAME_QUERY", "ROLE_NAME_COMMAND").collect(Collectors.toSet());
    }

    @Bean
    public SecurityApiKeyCredentialsDelegate getSecurityApiKeyCredentialsDelegate() {
        return apiKey -> new PrincipalCredential(apiKey, apiKey);
    }

}