package com.girikgarg.learningspringboot.Service;

import com.girikgarg.learningspringboot.DTO.PaymentRequest;
import com.girikgarg.learningspringboot.DTO.PaymentResponse;
import com.girikgarg.learningspringboot.Entity.PaymentEntity;
import com.girikgarg.learningspringboot.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentResponse getPaymentDetailsById(PaymentRequest paymentRequest) {
        PaymentEntity paymentModel = paymentRepository.getPaymentById(paymentRequest);

        // map it to response object
        PaymentResponse paymentResponse = mapModelToResponseDTO(paymentModel);

        return paymentResponse;
    }

    private PaymentResponse mapModelToResponseDTO(PaymentEntity paymentEntity) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(paymentEntity.getId());
        response.setAmount(paymentEntity.getPaymentAmount());
        response.setCurrency(paymentEntity.getPaymentCurrency());
        return response;
    }
}
