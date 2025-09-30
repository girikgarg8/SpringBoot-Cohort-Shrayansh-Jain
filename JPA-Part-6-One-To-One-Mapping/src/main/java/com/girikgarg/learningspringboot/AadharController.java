package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aadhar")
public class AadharController {

    @Autowired
    private AadharService aadharService;

    @GetMapping("/{id}")
    public Aadhar getAadhar(@PathVariable Long id) {
        return aadharService.getById(id);
    }
}
