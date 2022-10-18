

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.entity;

public class User {
    private Integer userId;
    private String userStatus;
    private String password;
    private String salt;
    private String userName;
    private String email;

    public User() {
    }

    public String toString() {
        return "User{userId=" + this.userId + ", userStatus='" + this.userStatus + '\'' + ", password='" + this.password + '\'' + ", salt='" + this.salt + '\'' + ", userName='" + this.userName + '\'' + ", email='" + this.email + '\'' + '}';
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getUserStatus() {
        return this.userStatus;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSalt() {
        return this.salt;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
