package com.accolite.app.controller;

import com.accolite.app.service.UserService;
import com.accolite.app.service.impl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/refresh-token/{id}")
    public String getRefreshToken(@PathVariable Long id, HttpServletRequest request) {
        return service.getRefreshToken(id, request);
    }


    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
