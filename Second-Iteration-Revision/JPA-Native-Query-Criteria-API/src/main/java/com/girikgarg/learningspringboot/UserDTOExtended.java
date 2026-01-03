package com.girikgarg.learningspringboot;

public class UserDTOExtended {
    private String userName;
    private String phone;
    private String city;
    
    public UserDTOExtended() {
    }
    
    public UserDTOExtended(String userName, String phone, String city) {
        this.userName = userName;
        this.phone = phone;
        this.city = city;
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
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
}



