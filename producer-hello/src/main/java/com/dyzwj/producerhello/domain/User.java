package com.dyzwj.producerhello.domain;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2020年01月17日 11:58:00
 */
public class User {

    private String username;

    private String password;

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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
