package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional
    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }
}


