package com.jb.app.controller;

import com.jb.app.beans.Category;
import com.jb.app.beans.Company;
import com.jb.app.beans.Coupon;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("companies/")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon addCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon) throws CouponSysException {
        return companyService.addCoupon(coupon,token);
    }

    @PutMapping("coupons/{id}") //id = couponID
    @ResponseStatus(HttpStatus.OK)
    public Coupon updateCoupon(@RequestHeader("Authorization") UUID token, @RequestBody Coupon coupon, @PathVariable int id) throws CouponSysException {
        return companyService.updateCoupon(token, coupon, id);
    }

    @DeleteMapping("coupons/{id}") //id = couponID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@RequestHeader("Authorization") UUID token,@PathVariable int id) throws CouponSysException {
        companyService.deleteCoupon(token, id);
    }

    @GetMapping("/coupons")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSysException {
        return companyService.getAllCoupons(token);
    }

    @GetMapping("/coupons/category")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getAllCouponsByCategory(@RequestHeader("Authorization") UUID token, @RequestParam Category category) throws CouponSysException {
        return companyService.getAllCouponsByCategory(category, token);
    }

    @GetMapping("/coupons/price")
    @ResponseStatus(HttpStatus.OK)
    public List<Coupon> getAllCouponsPriceLessThan(@RequestHeader("Authorization") UUID token, @RequestParam float price) throws CouponSysException {
        return companyService.getAllCouponsPriceLessThan(price, token);
    }

    @GetMapping("info")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyInfo(@RequestHeader("Authorization") UUID token) throws CouponSysException{
        return companyService.getCompanyInfo(token);
    }

    @GetMapping("/coupons/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Coupon getCoupon(@RequestHeader("Authorization") UUID token,@PathVariable int id) throws CouponSysException {
        return companyService.getCoupon(token, id);
    }
}
