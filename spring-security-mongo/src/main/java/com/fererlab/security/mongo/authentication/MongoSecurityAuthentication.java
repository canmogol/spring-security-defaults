package com.fererlab.security.mongo.authentication;

import com.fererlab.security.mongo.entity.MongoSecurityUserRoles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class MongoSecurityAuthentication implements Authentication {

    private final MongoSecurityUserRoles user;
    private boolean authenticated = false;

    public MongoSecurityAuthentication(MongoSecurityUserRoles user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return user.getUsername();
    }

    @Override
    public Object getDetails() {
        return user.getUsername();
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
