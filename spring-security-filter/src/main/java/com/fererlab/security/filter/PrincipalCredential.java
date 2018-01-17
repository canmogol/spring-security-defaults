package com.fererlab.security.filter;

public class PrincipalCredential {
    private Object principal;
    private Object credential;

    public PrincipalCredential(Object principal, Object credential) {
        this.principal = principal;
        this.credential = credential;
    }

    public Object getPrincipal() {
        return principal;
    }

    public Object getCredential() {
        return credential;
    }

}
