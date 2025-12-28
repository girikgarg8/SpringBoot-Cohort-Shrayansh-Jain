package com.girikgarg.learningspringboot;

public class UserDTO {
    private Long id;
    private String name;
    private String phone;
    private String address;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        System.out.println("Going to query address now");
        // preventing the Hibernate proxy internal fields from getting serialized by Jackson 
        // If Jackson picked Hibernate proxy internal fields for serialization, it would result in 500 error
        // But over here since we are explicitly picking street field only, that issue won't persist
        this.address = user.getUserAddress() != null ? user.getUserAddress().getStreet() : null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
