package com.girikgarg.learningspringboot;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerDetailsCK implements Serializable {
    private String name;
    private String address;

    public CustomerDetailsCK() {

    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerDetailsCK)) return false;
        CustomerDetailsCK customerDetails = (CustomerDetailsCK) obj;
        return customerDetails.name.equals(this.name) && customerDetails.address.equals(this.address);
    }
}
