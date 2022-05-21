package com.demo.demo.Model;

public class AuthenticationResponse { //output request

    private  final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() { //token
        return jwt;
    }
}
