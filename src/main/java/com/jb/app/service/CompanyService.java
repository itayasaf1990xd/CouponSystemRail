package com.jb.app.service;

import com.jb.app.beans.Category;
import com.jb.app.beans.Company;
import com.jb.app.beans.Coupon;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.security.LoginResponse;

import java.util.List;
import java.util.UUID;


public interface CompanyService {
    LoginResponse login(String email, String password) throws CouponSysException;

    Coupon addCoupon(Coupon coupon, UUID token) throws CouponSysException;

    Coupon updateCoupon(UUID token, Coupon coupon, int couponId) throws CouponSysException;

    void deleteCoupon(UUID token, int couponId) throws CouponSysException;

    List<Coupon> getAllCoupons(UUID token) throws CouponSysException;

    List<Coupon> getAllCouponsByCategory(Category category, UUID token) throws CouponSysException;

    List<Coupon> getAllCouponsPriceLessThan(float price, UUID token) throws CouponSysException;

    Company getCompanyInfo(UUID token) throws CouponSysException;

    Coupon getCoupon(UUID token, int couponId) throws CouponSysException;
}
