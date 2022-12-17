package com.jb.app.service;

import com.jb.app.beans.Company;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.security.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService{

    void isAdmin(UUID token) throws CouponSysException;

    LoginResponse login(String email, String password) throws CouponSysException;

    Company addCompany(Company company, UUID token) throws CouponSysException;

    Company updateCompany(UUID token, Company company, int companyId) throws CouponSysException;

    void deleteCompany(UUID token, int companyId) throws CouponSysException;

    List<Company> getAllCompanies(UUID token) throws CouponSysException;

    Company getOneCompany(UUID token, int id) throws CouponSysException;

    Customer addCustomer(Customer customer, UUID token) throws CouponSysException;

    Customer updateCustomer(int customerId, Customer customer, UUID token) throws CouponSysException;

    void deleteCustomer(int id, UUID token) throws CouponSysException;

    List<Customer> getAllCustomers(UUID token) throws CouponSysException;

    Customer getOneCustomer(int id, UUID token) throws CouponSysException;
}
