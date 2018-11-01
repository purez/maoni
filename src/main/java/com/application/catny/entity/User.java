package com.application.catny.entity;

public class User {
    private int pid;
    private String account;
    private String username;
    private String password;
    private String logined;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLogined() {
        return logined;
    }

    public void setLogined(String logined) {
        this.logined = logined;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
