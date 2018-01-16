package com.fererlab.security.mongo.provider;

import com.fererlab.security.mongo.entity.JpaSecurityUser;
import com.fererlab.security.mongo.repository.JpaSecurityUserRepository;
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
import java.util.stream.Collectors;

@Component
@Qualifier("JpaSecurityAuthenticationProvider")
public class JpaSecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JpaSecurityUserRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authentication and its name cannot be null
        if (authentication == null || authentication.getName() == null) {
            throw new BadCredentialsException("authentication and/or name cannot be null");
        }
        // check if the user exists
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        JpaSecurityUser user = repository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        // create authentication object and set its authentication to true by passing authorities
        List<SimpleGrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        // return authentication token
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AbstractAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
