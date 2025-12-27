package com.girikgarg.learningspringboot;

public class User {
    private int userId;
    private String name;
    private int age;

    // getters and setters

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return userId;
    }

    public int getAge() {
        return age;
    }
}
