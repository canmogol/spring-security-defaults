package com.fererlab.security.generic.provider;

import java.util.Set;

public interface AuthenticationDelegate {

    Set<String> findAuthorities(String principal, String credentials);

}
