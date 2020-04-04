package com.schedule.restservice.UserService;

public class User {

    //private final int id;

    private final  String userId;

    private final String userName;

    public User(String userName, String userId) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }
}