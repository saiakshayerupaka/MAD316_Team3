package com.cegep.lanbow.models;

public class Admin {
    public Admin() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public Admin(String user_name, String user_pass) {
        this.user_name = user_name;
        this.user_pass = user_pass;
    }

    private String user_name;
    private String user_pass;
}
