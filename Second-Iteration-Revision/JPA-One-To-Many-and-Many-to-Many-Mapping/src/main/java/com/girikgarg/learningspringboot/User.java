package com.girikgarg.learningspringboot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "APP_USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

   //  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Unidirectional relationship
    // @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    // Create a foreign key called user_id_fk in the order table which will reference the "id" field of User

    // Bidirectional relationship
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User() {

    }

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Helper method to maintain bidirectional relationship
    /**
     * Reason we need to set order.setUser(this)
     * JPA ONLY uses the owning side (Order.user) to persist the relationship to the database
     * 
     * Only the owning side (@JoinColumn) determines what gets saved
       The inverse side (mappedBy) is just for navigation
     * 
     * 
     */

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this); // IMPORTANT: Set the owning side
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null); // IMPORTANT: Clear the owning side
    }
}
