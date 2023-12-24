package com.accolite.app.controller;

import com.accolite.app.entity.PaymentEntity;
import com.accolite.app.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")

public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestBody PaymentEntity payment, HttpServletRequest request) {
        return paymentService.savePayment(payment,request);
    }

    @GetMapping("/{id}")
    public List<PaymentEntity> getAllPayments(@PathVariable Long id, HttpServletRequest request) {
        return paymentService.getPayment(id, request);
    }
}
