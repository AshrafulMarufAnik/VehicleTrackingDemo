package com.anik.armr.demoapptask.ModelClass;

public class User {
    private String username = "mail.muntasirhasan@gmail.com";
    private String password = "Test12345";
    private String session_id;
    private String user_id;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String session_id, String user_id) {
        this.username = username;
        this.password = password;
        this.session_id = session_id;
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
