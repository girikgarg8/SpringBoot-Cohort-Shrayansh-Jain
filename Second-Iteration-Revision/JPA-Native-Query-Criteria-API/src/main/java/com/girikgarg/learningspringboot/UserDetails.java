package com.girikgarg.learningspringboot;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "user_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@SqlResultSetMapping(
    name = "UserDTOMapping",
    classes = @ConstructorResult(
        targetClass = UserDTO.class,
        columns = {
            @ColumnResult(name = "user_name", type = String.class),
            @ColumnResult(name = "phone", type = String.class)
        }
    )
)
@NamedNativeQuery(
    name = "UserDetails.getUserDetailsByName",
    query = "SELECT user_name, phone FROM user_details WHERE user_name = :userFirstName",
    resultSetMapping = "UserDTOMapping"
)
public class UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "user_name")
    private String name;
    
    private String phone;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private UserAddress userAddress;
    
    public UserDetails() {
    }
    
    public UserDetails(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    public UserDetails(String name, String phone, UserAddress userAddress) {
        this.name = name;
        this.phone = phone;
        this.userAddress = userAddress;
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public UserAddress getUserAddress() {
        return userAddress;
    }
    
    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}

