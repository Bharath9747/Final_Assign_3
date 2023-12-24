package com.accolite.app.service;

import com.accolite.app.entity.PaymentEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    List<PaymentEntity> getAllPayments();

    ResponseEntity<String> savePayment(PaymentEntity payment, HttpServletRequest request);


    ResponseEntity<String> approvePayment(Long id, int option);


    List<PaymentEntity> getPayment(Long id, HttpServletRequest request);

    List<PaymentEntity> getPayment(Long id);
}
