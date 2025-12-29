package com.girikgarg.learningspringboot;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class UserSpecification {
    
    public static Specification<UserDetails> equalsPhone(String phoneNo) {
        return (root, query, cb) -> {
            return cb.equal(root.get("phone"), phoneNo);
        };
    }
    
    public static Specification<UserDetails> likeName(String name) {
        return (root, query, cb) -> {
            return cb.like(root.get("name"), "%" + name + "%");
        };
    }
    
    public static Specification<UserDetails> joinAddress() {
        return (root, query, cb) -> {
            Join<UserDetails, UserAddress> address = root.join("userAddress", JoinType.INNER);
            return null;
        };
    }
}

