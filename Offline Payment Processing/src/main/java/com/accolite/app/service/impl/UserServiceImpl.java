package com.accolite.app.service.impl;

import com.accolite.app.entity.TokenCheck;
import com.accolite.app.handler.ExceptionHandler;
import com.accolite.app.repository.TokenCheckRepository;
import com.accolite.app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    TokenCheckRepository tokenCheckRepository;

    @Override
    public String getRefreshToken(Long id, HttpServletRequest request) {
        TokenCheck token = tokenCheckRepository.findById(id).get();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String headerToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No Record Exists");
        }
        headerToken = authHeader.substring(7);
        if (token.getJwttoken().equals(headerToken))
            return token.getRefreshtoken();
        throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No Record Exists");
    }

    @Override
    public Boolean userCred(Long id, HttpServletRequest request) {
        TokenCheck token = tokenCheckRepository.findById(id).get();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String headerToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No Record Exists");
        }
        headerToken = authHeader.substring(7);
        if (token.getJwttoken().equals(headerToken))
            return true;
        return false;
    }


}
