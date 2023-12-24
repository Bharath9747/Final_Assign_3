package com.accolite.app.service.impl;

import com.accolite.app.entity.UsersEntity;
import com.accolite.app.enumType.Status;
import com.accolite.app.handler.ExceptionHandler;
import com.accolite.app.repository.UsersRepository;
import com.accolite.app.service.AdminService;
import com.accolite.app.service.PaymentService;
import com.accolite.app.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentService paymentService;


    @Override
    public List<UsersEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<String> approveUser(Long id) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);
        if (usersEntity != null) {
            if (!(usersEntity.getRole().ordinal() == 0)) {
                usersEntity.setStatus(Status.APPROVED);
                usersRepository.save(usersEntity);
                return new ResponseEntity<>("Approved " + usersEntity.getName(), HttpStatus.ACCEPTED);

            }
            return new ResponseEntity<>("Admin cannot approve Himself",HttpStatus.BAD_REQUEST);
        }
        throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No User Exists");
    }

    @Override
    @Transactional
    public ResponseEntity<String> activateWallet(Long id) {
        UsersEntity usersEntity = usersRepository.findById(id).orElse(null);
        if (usersEntity != null) {
            if (usersEntity.getStatus().ordinal() != 0) {
                if (usersEntity.getRole().ordinal() != 0) {
                    return walletService.setWalletStatus(id);
                } else
                    return new ResponseEntity<>("Already Activated for Admin",HttpStatus.BAD_REQUEST);
            } else
                return new ResponseEntity<>("Activate the account",HttpStatus.BAD_REQUEST);

        }
        throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No User Exists");
    }

    @Override

    public ResponseEntity<String> approvePayment(Long id, int option) {
        return paymentService.approvePayment(id, option);
    }
}
