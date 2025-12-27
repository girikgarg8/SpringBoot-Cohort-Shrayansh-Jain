package com.girikgarg.learningspringboot;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Entity;

public class PersonDetailsCK implements Serializable {
    private String name;
    private String address;
    
    public PersonDetailsCK() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PersonDetailsCK)) return false;
        PersonDetailsCK person = (PersonDetailsCK) obj;
        return person.name.equals(this.name) && person.address.equals(this.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
