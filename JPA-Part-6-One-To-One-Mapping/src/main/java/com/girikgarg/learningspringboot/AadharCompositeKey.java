package com.girikgarg.learningspringboot;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AadharCompositeKey implements Serializable {
    private String phone;
    private String email;

    public AadharCompositeKey() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (!(obj instanceof AadharCompositeKey)) return false;
        AadharCompositeKey other = (AadharCompositeKey) obj;
        return phone.equals(other.phone) && email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }
}
