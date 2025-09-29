package com.girikgarg.learningspringboot;

import jakarta.persistence.*;

@Entity
public class Aadhar {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @EmbeddedId
    private AadharCompositeKey aadharCompositeKey;

    public Aadhar() { }

}
