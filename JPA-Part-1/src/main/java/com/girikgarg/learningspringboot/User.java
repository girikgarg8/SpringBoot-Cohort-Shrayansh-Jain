package com.girikgarg.learningspringboot;

public class User {
    private String username;
    private int userId;
    private int age;

    public User() {

    }

    public User(String username, int userId, int age) {
        this.username = username;
        this.userId = userId;
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
