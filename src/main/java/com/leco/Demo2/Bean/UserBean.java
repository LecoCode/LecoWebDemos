package com.leco.Demo2.Bean;

/**
 * 注册Bean
 */
public class UserBean {
    private String id;
    private int password;
    private int passwordCopy;
    private String username;
    private String age;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPasswordCopy() {
        return passwordCopy;
    }

    public void setPasswordCopy(int passwordCopy) {
        this.passwordCopy = passwordCopy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
