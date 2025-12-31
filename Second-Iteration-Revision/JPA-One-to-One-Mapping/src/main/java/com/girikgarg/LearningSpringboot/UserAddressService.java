package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Transactional(readOnly = true)
    public UserAddress getUserAddress(Long id) {
        return userAddressRepository.findById(id).orElse(null);
    }
}



