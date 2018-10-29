package com.application.catny.entity;

public class User {
    private int pid;
    private String username;
    private String password;
    private String logined;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public String getLogined() {
        return logined;
    }

    public void setLogined(String logined) {
        this.logined = logined;
    }
}
