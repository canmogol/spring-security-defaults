package com.fererlab.security.filter;

import java.util.Set;

@FunctionalInterface
public interface SecurityApiKeyAuthoritiesDelegate {

    Set<String> authenticate(String apiKey);

}
