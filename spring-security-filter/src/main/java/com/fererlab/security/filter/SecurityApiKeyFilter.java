package com.fererlab.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("SecurityApiKeyFilter")
public class SecurityApiKeyFilter extends OncePerRequestFilter {

    private static final String DEFAULT_API_KEY = "X-API-Key";

    @Value("${spring.security.apiKeyName}")
    private String apiKeyName;

    @Autowired(required = false)
    private SecurityApiKeyAuthoritiesDelegate authoritiesDelegate;

    @Autowired(required = false)
    private SecurityApiKeyCredentialsDelegate credentialsDelegate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // check if there the delegate exists
        if (authoritiesDelegate == null) {
            throw new NullPointerException("There is no SecurityApiKeyAuthoritiesDelegate instance found, implement SecurityApiKeyAuthoritiesDelegate interface and set it as a spring bean.");
        }
        // if api header is not overridden then use the default
        if (apiKeyName == null) {
            apiKeyName = DEFAULT_API_KEY;
        }
        // get api key from header
        String apiKey = request.getHeader(apiKeyName);
        if (apiKey != null) {
            // authenticate and convert to granted authorities
            Set<String> authorities = authoritiesDelegate.authenticate(apiKey);
            List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

            // remove current authentication
            SecurityContextHolder.getContext().setAuthentication(null);

            // try to find username and password if available
            Object principal = apiKey;
            Object credential = apiKey;
            if (credentialsDelegate != null) {
                PrincipalCredential credentials = credentialsDelegate.credentials(apiKey);
                principal = credentials.getPrincipal();
                credential = credentials.getCredential();
            }

            // create authentication token
            PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                principal,
                credential,
                grantedAuthorities
            );

            // set token to security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}
