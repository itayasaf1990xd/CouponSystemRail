package com.jb.app.service;

import com.jb.app.beans.Company;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.exceptions.ExceptionType;
import com.jb.app.login.ClientType;
import com.jb.app.repo.CompanyRepository;
import com.jb.app.repo.CouponRepository;
import com.jb.app.repo.CustomerRepo;
import com.jb.app.security.Information;
import com.jb.app.security.LoginResponse;
import com.jb.app.security.TokenManager;
import com.jb.app.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class AdminServiceImpl extends ClientService implements AdminService{

    public AdminServiceImpl(CustomerRepo customerRepo, CompanyRepository companyRepo, CouponRepository couponRepo, TokenManager tokenManager) {
        super(customerRepo, companyRepo, couponRepo, tokenManager);
    }

    @Override
    public void isAdmin(UUID token) throws CouponSysException {
        if (!tokenManager.isAuthorized(token, ClientType.ADMINISTRATOR)) {
            throw new CouponSysException(ExceptionType.UNAUTHORIZED_ACTION);
        }
    }

    @Override
    public LoginResponse login(String email, String password) throws CouponSysException {
        if (!(email.equalsIgnoreCase("admin@admin.com")&& password.equals("Admin123"))) throw new CouponSysException(ExceptionType.INVALID_EMAIL_AND_PASSWORD);
        Information information = Information.builder().expirationTime(LocalDateTime.now().plusHours(30)).email("admin@admin.com").clientType(ClientType.ADMINISTRATOR).id(-1).build();

        UUID token = tokenManager.addToken(information);
        if (token == null) throw new CouponSysException(ExceptionType.INCORRECT_LOGIN);
        return new LoginResponse(tokenManager.addToken(information),"admin@admin.com", -1, "Admin", ClientType.ADMINISTRATOR);
    }

    @Override
    public Company addCompany(Company company, UUID token) throws CouponSysException {
        company.setEmail(company.getEmail().toLowerCase(Locale.ROOT));

        isAdmin(token);

        if (!ValidationUtils.isValidPassword(company.getPassword())) throw new CouponSysException((ExceptionType.INVALID_PASSWORD));
        if (!ValidationUtils.isValidEmail(company.getEmail())) throw new CouponSysException(ExceptionType.INVALID_EMAIL);

        System.out.println(companyRepo.existsByNameOrEmailOrId(company.getName(), company.getEmail(), company.getId()));
        System.out.println(companyRepo.existsByEmail(company.getEmail()));
        if(companyRepo.existsByNameOrEmailOrId(company.getName(), company.getEmail(), company.getId())) throw new CouponSysException(ExceptionType.INVALID_CHANGE_REQUEST);
        companyRepo.save(company);
        return company;
    }

    @Override
    @Transactional
    public Company updateCompany(UUID token, Company company, int companyId) throws CouponSysException {
        System.out.println("vefore: " + company);
        isAdmin(token);

        if (!ValidationUtils.isValidPassword(company.getPassword())) throw new CouponSysException(ExceptionType.INVALID_PASSWORD);

        Company temp = companyRepo.findById(companyId).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));

        if (!company.getName().equalsIgnoreCase(temp.getName())) throw new CouponSysException(ExceptionType.INVALID_CHANGE_REQUEST);

        company.setId(companyId); //insurance policy, no more hackers with hoodies

        System.out.println("after method: " + company);
        companyRepo.saveAndFlush(company);
        return company;
    }

    @Override
    public void deleteCompany(UUID token, int companyId) throws CouponSysException {
        isAdmin(token);

        couponRepo.deletePurchasesByCompanyId(companyId);
        companyRepo.deleteById(companyId);
    }

    @Override
    public List<Company> getAllCompanies(UUID token) throws CouponSysException {
        isAdmin(token);
        return companyRepo.findAll();
    }

    @Override
    public Company getOneCompany(UUID token, int id) throws CouponSysException {
        isAdmin(token);
        return companyRepo.findById(id).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));
    }

    @Override
    public Customer addCustomer(Customer customer, UUID token) throws CouponSysException {
        isAdmin(token);
        if (!ValidationUtils.isValidPassword(customer.getPassword())) throw new CouponSysException(ExceptionType.INVALID_PASSWORD);
        if (!ValidationUtils.isValidEmail(customer.getEmail())) throw new CouponSysException(ExceptionType.INVALID_EMAIL);
        if (customerRepo.existsByEmailAndId(customer.getEmail(), customer.getId())) throw new CouponSysException(ExceptionType.EMAIL_ALREADY_EXISTS);
        customerRepo.save(customer);
        return customer;
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer, UUID token) throws CouponSysException {
        isAdmin(token);
        if (!customerRepo.existsById(customer.getId())) throw new CouponSysException(ExceptionType.NO_SUCH_ID);
        if (!customerRepo.existsByEmailAndId(customer.getEmail(), customer.getId())) throw new CouponSysException(ExceptionType.INVALID_CHANGE_REQUEST);
        customerRepo.saveAndFlush(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(int id, UUID token) throws CouponSysException {
        isAdmin(token);
        if (!customerRepo.existsById(id)) throw new CouponSysException(ExceptionType.NO_SUCH_ID);
        couponRepo.deletePurchasesByCustomerId(id);
        customerRepo.deleteById(id);

    }

    @Override
    public List<Customer> getAllCustomers(UUID token) throws CouponSysException {
        isAdmin(token);
        return customerRepo.findAll();
    }

    @Override
    public Customer getOneCustomer(int id, UUID token) throws CouponSysException {
        isAdmin(token);
        return customerRepo.findById(id).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));
    }


}
