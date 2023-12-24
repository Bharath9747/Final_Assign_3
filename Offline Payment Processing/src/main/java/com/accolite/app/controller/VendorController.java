package com.accolite.app.controller;

import com.accolite.app.entity.PaymentEntity;
import com.accolite.app.entity.WalletEntity;
import com.accolite.app.service.PaymentService;
import com.accolite.app.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vendor")

public class VendorController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private WalletService walletService;

    @GetMapping("/payment/{id}")
    public List<PaymentEntity> getAllPayments(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    @GetMapping("/wallet/{id}")
    public WalletEntity getWallet(@PathVariable Long id) {
        return walletService.getWallet(id);
    }
}
