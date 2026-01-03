package com.girikgarg.learningspringboot;

public class UserDTO {
    private String userName;
    private String phone;
    
    public UserDTO() {
    }
    
    public UserDTO(String userName, String phone) {
        this.userName = userName;
        this.phone = phone;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}



