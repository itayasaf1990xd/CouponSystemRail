package com.jb.app.login;

import com.jb.app.exceptions.CouponSysException;
import com.jb.app.exceptions.ExceptionType;
import com.jb.app.security.LoginResponse;
import com.jb.app.service.AdminService;
import com.jb.app.service.ClientService;
import com.jb.app.service.CompanyService;
import com.jb.app.service.CustomerService;
import com.jb.app.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LoginManager {
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final AdminService adminService;

    public LoginResponse login(String email, String password, ClientType type) throws CouponSysException {

        if (!ValidationUtils.isValidPassword(password)) throw new CouponSysException(ExceptionType.INVALID_PASSWORD);
        if (!ValidationUtils.isValidEmail(email)) throw new CouponSysException(ExceptionType.INVALID_EMAIL);


        LoginResponse response = null;

        switch (type) {
            case ADMINISTRATOR: response = adminService.login(email, password);
                break;
            case COMPANY: response = companyService.login(email, password);
                break;
            case CUSTOMER: response = customerService.login(email, password);
                break;
        }

        return response;
    }
}
