package com.girikgarg.learningspringboot;

public class UserDTO {
    private Long userId;
    private String name;
    private Long aadharId;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        System.out.println("Going to query the user aadahar ID now");
        this.aadharId = user.getAadhar() != null ? user.getAadhar().getId() : null;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getAadharId() {
        return aadharId;
    }
    public void setAadharId(Long aadharId) {
        this.aadharId = aadharId;
    }
}
