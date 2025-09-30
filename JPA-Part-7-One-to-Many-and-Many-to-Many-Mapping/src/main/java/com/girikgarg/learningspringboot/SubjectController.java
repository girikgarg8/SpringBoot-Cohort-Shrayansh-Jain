package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * curl -X POST http://localhost:8080/api/subjects \
     *   -H 'Content-Type: application/json' \
     *   -d '{"name":"Mathematics"}'
     *
     */
    @PostMapping
    public Subject create(@RequestBody Subject subject) {
        return subjectService.create(subject);
    }
}


