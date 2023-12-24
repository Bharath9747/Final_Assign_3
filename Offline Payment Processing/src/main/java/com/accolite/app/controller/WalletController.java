package com.accolite.app.controller;

import com.accolite.app.entity.WalletEntity;
import com.accolite.app.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")

public class WalletController {
    @Autowired
    private WalletService walletService;


    @GetMapping("/addmoney/{id}/{amount}")
    public ResponseEntity<String> addMoney(@PathVariable Long id, @PathVariable Double amount, HttpServletRequest request) {
        return walletService.addMoney(id, amount, request);
    }

    @GetMapping("/changepayment/{id}")
    public ResponseEntity<String> changePayment(@PathVariable Long id, HttpServletRequest request) {
        return walletService.changePaymentType(id, request);
    }

    @GetMapping("/{id}")
    public WalletEntity getWallet(@PathVariable Long id, HttpServletRequest request) {
        return walletService.getWallet(id, request);
    }

}
