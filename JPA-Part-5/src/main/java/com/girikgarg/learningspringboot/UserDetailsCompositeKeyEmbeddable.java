package com.girikgarg.learningspringboot;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserDetailsCompositeKeyEmbeddable implements Serializable {
    private String name;
    private String email;

    public UserDetailsCompositeKeyEmbeddable() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (!(obj instanceof UserDetailsCompositeKeyEmbeddable)) return false;
        UserDetailsCompositeKeyEmbeddable other = (UserDetailsCompositeKeyEmbeddable) obj;
        return other.name.equals(this.name) && other.email.equals(this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
