//package com.girikgarg.learningspringboot;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/v1")
//public class PaymentController {
//    @Autowired
//    @Qualifier("upiPaymentStrategy")
//    private PaymentStrategy upiPaymentStrategy;
//
//    @Autowired
//    @Qualifier("creditCardPaymentStrategy")
//    private PaymentStrategy creditCardPaymentStrategy;
//
//    @PostMapping("/payments")
//    public String doPayment(@RequestParam boolean isUpi) {
//        if (isUpi) return upiPaymentStrategy.pay();
//        else return creditCardPaymentStrategy.pay();
//    }
//}
