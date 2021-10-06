package com.project.model.security;

import java.io.Serializable;

public class JwtChangePassRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String passwordOld;
    private String passwordNew;

    public JwtChangePassRequest() {
    }

    public JwtChangePassRequest(String username, String passwordOld, String passwordNew) {
        this.setUsername(username);
        this.setPasswordOld(passwordOld);
        this.setPasswordNew(passwordNew);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
}