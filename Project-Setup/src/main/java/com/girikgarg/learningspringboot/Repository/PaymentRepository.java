package com.girikgarg.learningspringboot.Repository;

import com.girikgarg.learningspringboot.DTO.PaymentRequest;
import com.girikgarg.learningspringboot.Entity.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {
    public PaymentEntity getPaymentById(PaymentRequest paymentRequest) {
        PaymentEntity paymentModel = executeQuery(paymentRequest);
        return paymentModel;
    }

    private PaymentEntity executeQuery(PaymentRequest paymentRequest) {
        // connect with DB and fetch the data
        PaymentEntity payment = new PaymentEntity();
        payment.setId(paymentRequest.getPaymentId());
        payment.setPaymentCurrency("INR");
        payment.setPaymentAmount(100.00);
        return payment;
    }
}
