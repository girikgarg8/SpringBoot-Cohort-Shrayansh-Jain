package com.girikgarg.learningspringboot;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AadharCompositeKey implements Serializable {
    private String phone;
    private String email;

    public AadharCompositeKey() { }

    public AadharCompositeKey(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (!(obj instanceof AadharCompositeKey)) return false;
        AadharCompositeKey other = (AadharCompositeKey) obj;
        return Objects.equals(phone, other.phone) && Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email);
    }
}
