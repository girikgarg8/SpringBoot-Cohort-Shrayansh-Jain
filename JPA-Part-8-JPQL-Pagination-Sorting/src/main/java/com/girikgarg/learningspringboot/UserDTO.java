package com.girikgarg.learningspringboot;

public class UserDTO {
    private String username;
    private String phone;

    public UserDTO() {

    }

    public UserDTO(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
