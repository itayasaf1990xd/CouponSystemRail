package com.jb.app.service;

import com.jb.app.beans.Category;
import com.jb.app.beans.Coupon;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.security.LoginResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    LoginResponse login(String email, String password) throws CouponSysException;

    Coupon purchaseCoupon(int couponId, UUID token) throws CouponSysException;

    Customer getCustomerDetails(UUID token) throws CouponSysException;

    List<Coupon> getCustomerCouponsByMaxPrice(UUID token, double maxPrice) throws CouponSysException;

    List<Coupon> getCustomerCoupons(UUID token) throws CouponSysException;

    List<Coupon> getAllCoupons(UUID token) throws CouponSysException;

    List<Coupon> getCustomerCouponsByCategory(UUID token, Category category) throws CouponSysException;
}
