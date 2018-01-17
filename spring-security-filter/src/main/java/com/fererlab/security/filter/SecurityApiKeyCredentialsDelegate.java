package com.fererlab.security.filter;

@FunctionalInterface
public interface SecurityApiKeyCredentialsDelegate {

    PrincipalCredential credentials(String apiKey);

}
