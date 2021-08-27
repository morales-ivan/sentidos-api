package com.sirius.sentidosapi.config.security;

public class JWT {
    public JWT(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    private String jwt;
}
