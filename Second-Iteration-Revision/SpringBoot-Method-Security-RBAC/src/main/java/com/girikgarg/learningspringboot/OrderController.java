package com.girikgarg.learningspringboot;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    
    @GetMapping("/orders")
    @PreAuthorize("hasRole('USER') and hasAuthority('ORDER_READ')")
    @PostAuthorize("returnObject.userID == authentication.principal.id")
    public OrderDTO readOrders() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserID(11L);
        orderDTO.setOrderID(100001L);
        return orderDTO;
    }
}

