package com.fererlab.security.mongo.provider;

import com.fererlab.security.mongo.authentication.MongoSecurityAuthentication;
import com.fererlab.security.mongo.entity.MongoSecurityUserRoles;
import com.fererlab.security.mongo.repository.MongoSecurityUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MongoSecurityAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MongoSecurityUserDetailsRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authentication and its name cannot be null
        if (authentication == null || authentication.getName() == null) {
            throw new BadCredentialsException("authentication and/or authentication name cannot be null");
        }
        // check if the user exists
        MongoSecurityUserRoles userRoles = repository.findByUsername(authentication.getName());
        if (userRoles == null) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        // create authentication object and set its authentication to true only if the user is active
        MongoSecurityAuthentication auth = new MongoSecurityAuthentication(userRoles);
        if (userRoles.isEnabled() && userRoles.isAccountNonExpired() && userRoles.isAccountNonLocked() && userRoles.isCredentialsNonExpired()) {
            auth.setAuthenticated(true);
        }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MongoSecurityAuthentication.class.isAssignableFrom(authentication);
    }

}
