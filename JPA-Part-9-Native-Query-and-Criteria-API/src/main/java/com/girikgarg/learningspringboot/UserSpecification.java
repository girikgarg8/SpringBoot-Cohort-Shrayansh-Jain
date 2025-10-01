package com.girikgarg.learningspringboot;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> equalsName(String userName) {
        return (root, query, cb) -> cb.equal(root.get("name"), userName);
    }

    public static Specification<User> equalsId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
