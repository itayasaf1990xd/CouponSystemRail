package com.jb.app.controller;

import com.jb.app.beans.Category;
import com.jb.app.beans.Coupon;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("customers/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PutMapping("/coupons/{id}") // id = couponId
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseCoupon(@PathVariable int id, @RequestHeader("Authorization") UUID token) throws CouponSysException {
        customerService.purchaseCoupon(id, token);
    }

    @GetMapping("info")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return customerService.getCustomerDetails(token);
    }

    @GetMapping("coupons/price")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getCustomerCouponsByMaxPrice(@RequestHeader("Authorization") UUID token, @RequestParam double maxPrice) throws CouponSysException {
        return customerService.getCustomerCouponsByMaxPrice(token, maxPrice);
    }

    @GetMapping("coupons/category")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getCustomerCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSysException {
        return customerService.getCustomerCouponsByCategory(token, category);
    }

    @GetMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getAllCouponsById(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return customerService.getCustomerCoupons(token);
    }

    @GetMapping("/coupons/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return customerService.getAllCoupons(token);
    }

}