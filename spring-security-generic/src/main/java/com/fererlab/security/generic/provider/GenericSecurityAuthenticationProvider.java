package com.fererlab.security.generic.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("GenericSecurityAuthenticationProvider")
public class GenericSecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired(required = false)
    AuthenticationDelegate authenticationDelegate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authentication and its name cannot be null
        if (authentication == null || authentication.getName() == null) {
            throw new BadCredentialsException("authentication and/or name cannot be null");
        }
        // check if the user exists
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        // check if there is any authentication delegate exist
        if (authenticationDelegate == null) {
            throw new NullPointerException("There is no AuthenticationDelegate instance found, implement authenticationDelegate interface and wire it as a spring bean.");
        }
        // create authentication object and set its authentication to true by passing authorities
        Set<String> authorities = authenticationDelegate.findAuthorities(username, password);
        if (authorities == null) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        // create granted authorities
        List<SimpleGrantedAuthority> grantedAuthorities = authorities
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        // return authentication token
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AbstractAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
