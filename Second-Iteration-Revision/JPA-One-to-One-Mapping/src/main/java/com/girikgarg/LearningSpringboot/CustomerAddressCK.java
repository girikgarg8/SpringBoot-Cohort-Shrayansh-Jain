package com.girikgarg.learningspringboot;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerAddressCK implements Serializable {
    private String street;
    private String pinCode;

    public CustomerAddressCK() {
    }

    public CustomerAddressCK(String street, String pinCode) {
        this.street = street;
        this.pinCode = pinCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAddressCK)) return false;
        CustomerAddressCK that = (CustomerAddressCK) o;
        return Objects.equals(street, that.street) && Objects.equals(pinCode, that.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, pinCode);
    }
}
