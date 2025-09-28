package com.girikgarg.learningspringboot;

import java.io.Serializable;
import java.util.Objects;

public class UserDetailsCompositeKey implements Serializable {
    private String name;
    private String email;

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (!(obj instanceof UserDetailsCompositeKey)) return false;
        UserDetailsCompositeKey other = (UserDetailsCompositeKey) obj;
        return other.name.equals(this.name) && other.email.equals(this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
