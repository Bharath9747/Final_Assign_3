package com.accolite.app.service;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    String getRefreshToken(Long id, HttpServletRequest request);

    Boolean userCred(Long id, HttpServletRequest request);

}
