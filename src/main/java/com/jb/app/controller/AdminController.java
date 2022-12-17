package com.jb.app.controller;

import com.jb.app.beans.Company;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("admin/")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("companies")
    @ResponseStatus(HttpStatus.CREATED)
    public Company addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSysException {
        return adminService.addCompany(company, token);
    }

    @PutMapping("companies/{id}") //id = companyId
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Company company) throws CouponSysException {
        return adminService.updateCompany(token, company, id);
    }

    @GetMapping("companies")
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return adminService.getAllCompanies(token);
    }

    @GetMapping("companies/{id}")//id = companyId
    @ResponseStatus(HttpStatus.OK)
    public Company getCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSysException {
        return adminService.getOneCompany(token, id);
    }

    @DeleteMapping("companies/{id}")//id = companyId
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSysException {
        adminService.deleteCompany(token, id);
    }


    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSysException {
        return adminService.addCustomer(customer, token);
    }

    @PutMapping("customers/{id}")//id = customerId
    @ResponseStatus(HttpStatus.OK)
    public Customer UpdateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id, @RequestBody Customer customer) throws CouponSysException {
        return adminService.updateCustomer(id, customer, token);
    }

    @GetMapping("customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return adminService.getAllCustomers(token);
    }

    @GetMapping("customers/{id}")//id = customerId
    public Customer getCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSysException {
        return adminService.getOneCustomer(id, token);
    }

    @DeleteMapping("customers/{id}")//id = customerId
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int id) throws CouponSysException {
        adminService.deleteCustomer(id, token);
    }


}
