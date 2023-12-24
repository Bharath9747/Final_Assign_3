package com.accolite.app.service.impl;


import com.accolite.app.config.JwtService;
import com.accolite.app.entity.*;
import com.accolite.app.enumType.Status;
import com.accolite.app.enumType.TokenType;
import com.accolite.app.handler.ExceptionHandler;
import com.accolite.app.repository.TokenCheckRepository;
import com.accolite.app.repository.TokenRepository;
import com.accolite.app.repository.UsersRepository;
import com.accolite.app.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UsersRepository repository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenCheckRepository tokenCheckRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private WalletService walletService;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UsersEntity.builder()
                .name(request.getName())
                .status(getStatus(request.getRole().ordinal()))
                .lattitude(request.getLatitude())
                .longitude(request.getLongitude())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var wallet = WalletEntity.builder()
                .id(savedUser.getId())
                .build();
        walletService.saveWallet(wallet, savedUser.getRole().ordinal());
        if (user.getRole().ordinal() == 2) {
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            saveTokens(savedUser, jwtToken, refreshToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else
            return AuthenticationResponse.builder().refreshToken(null).accessToken(null).build();
    }

    private Status getStatus(int role_id) {
        return role_id == 0 ? Status.APPROVED : Status.NOT_APPROVED;
    }

    @Transactional
    private void saveUserToken(UsersEntity user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Transactional
    private void saveTokens(UsersEntity user, String jwtToken, String refreshToken) {
        var token = TokenCheck.builder()
                .id(user.getId())
                .jwttoken(jwtToken)
                .refreshtoken(refreshToken)
                .build();
        tokenCheckRepository.save(token);
    }

    @Transactional
    private void revokeAllUserTokens(UsersEntity user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No User Exists");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                refreshToken = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                updateToken(user, accessToken, refreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        } else
            throw new ExceptionHandler(HttpStatus.NOT_FOUND, "No User Exists");
    }

    private void updateToken(UsersEntity user, String accessToken, String refreshToken) {
        var token = tokenCheckRepository.findById(user.getId()).get();
        token.setJwttoken(accessToken);
        token.setRefreshtoken(refreshToken);
        tokenCheckRepository.save(token);
    }
}