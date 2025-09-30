package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * curl -X POST http://localhost:8080/api/teachers \
     *   -H 'Content-Type: application/json' \
     *   -d '{
     *     "name": "Mr. Smith",
     *     "subjects": [
     *       {"id": 1, "name": "Mathematics"}
     *     ]
     *   }'
     *
     */

    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return teacherService.create(teacher);
    }
}


