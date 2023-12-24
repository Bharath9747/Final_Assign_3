package com.accolite.app.service;

import com.accolite.app.entity.WalletEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WalletService {
    List<WalletEntity> getAllWalletDetails();

    void saveWallet(WalletEntity wallet, int id);

    ResponseEntity<String> addMoney(Long id, Double amount, HttpServletRequest request);

    ResponseEntity<String> setWalletStatus(Long id);

    ResponseEntity<String> changePaymentType(Long id, HttpServletRequest request);

    void updateWallet(Long vendorId, Long walletId, Double amount);

    void updateWallet(Long userId, Double amount);


    WalletEntity getWallet(Long id, HttpServletRequest request);

    WalletEntity getWallet(Long id);
}
