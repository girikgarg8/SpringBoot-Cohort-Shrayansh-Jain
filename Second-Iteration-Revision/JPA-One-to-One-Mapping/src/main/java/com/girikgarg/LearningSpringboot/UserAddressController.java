package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-address")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/{id}")
    public UserAddress getUserAddress(@PathVariable Long id) {
        return userAddressService.getUserAddress(id);
    }
}


