package com.fererlab.security.generic.provider;

import java.util.Set;

@FunctionalInterface
public interface AuthenticationDelegate {

    Set<String> findAuthorities(String principal, String credentials);

}
