package com.fererlab.security.mongo.provider;

import com.fererlab.security.mongo.entity.MongoSecurityUserRoles;
import com.fererlab.security.mongo.repository.MongoSecurityUserDetailsRepository;
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
@Qualifier("MongoSecurityAuthenticationProvider")
public class MongoSecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MongoSecurityUserDetailsRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authentication and its name cannot be null
        if (authentication == null || authentication.getName() == null) {
            throw new BadCredentialsException("authentication and/or name cannot be null");
        }
        // check if the user exists
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        MongoSecurityUserRoles user = repository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        // create authentication object and set its authentication to true by passing authorities
        List<SimpleGrantedAuthority> authorities = user.getRoles()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        // return authentication token
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AbstractAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
