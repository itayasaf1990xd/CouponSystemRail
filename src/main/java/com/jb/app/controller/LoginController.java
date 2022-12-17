package com.jb.app.controller;

import com.jb.app.exceptions.CouponSysException;
import com.jb.app.login.LoginManager;
import com.jb.app.security.LoginRequest;
import com.jb.app.security.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    private final LoginManager loginManager;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) throws CouponSysException {
        return loginManager.login(request.getEmail(), request.getPassword(), request.getClientType());
    }
}
