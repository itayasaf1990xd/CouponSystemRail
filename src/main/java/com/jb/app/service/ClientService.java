package com.jb.app.service;

import com.jb.app.exceptions.CouponSysException;
import com.jb.app.repo.CompanyRepository;
import com.jb.app.repo.CouponRepository;
import com.jb.app.repo.CustomerRepo;
import com.jb.app.security.LoginResponse;
import com.jb.app.security.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public abstract class ClientService {
    protected final CustomerRepo customerRepo;
    protected final CompanyRepository companyRepo;
    protected final CouponRepository couponRepo;
    protected final TokenManager tokenManager;

    public abstract LoginResponse login(String email, String password) throws CouponSysException;
}
