package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AadharService {
    @Autowired
    private AadharRepository aadharRepository;

    public Aadhar getById(Long id) {
        return aadharRepository.findById(id).get();
    }
}
