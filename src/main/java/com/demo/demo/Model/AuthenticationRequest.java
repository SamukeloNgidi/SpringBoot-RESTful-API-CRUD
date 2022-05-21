package com.demo.demo.Model;

public class AuthenticationRequest { //input request
    //this is what the user is going to send in the post request
    private String username;
    private String password;

    public AuthenticationRequest(){ //for serialisation
    }

    public AuthenticationRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
