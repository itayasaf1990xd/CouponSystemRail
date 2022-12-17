package com.jb.app.service;

import com.jb.app.beans.Category;
import com.jb.app.beans.Coupon;
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
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl extends ClientService implements CustomerService {


    public CustomerServiceImpl(CustomerRepo customerRepo, CompanyRepository companyRepo, CouponRepository couponRepo, TokenManager tokenManager) {
        super(customerRepo, companyRepo, couponRepo, tokenManager);
    }

    @Override
    public LoginResponse login(String email, String password) throws CouponSysException {
        System.out.println("got to login method");
        Customer result = customerRepo.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSysException(ExceptionType.INVALID_EMAIL_AND_PASSWORD));

        Information information = Information.builder().id(result.getId()).email(email).clientType(ClientType.CUSTOMER).expirationTime(LocalDateTime.now().plusHours(30)).build();
        String name = result.getFirstName() + " " + result.getLastName();

        UUID token = tokenManager.addToken(information);
        return new LoginResponse(token, email, result.getId(), name, ClientType.CUSTOMER);
    }

    /**
     * updates customer's coupons array to include the coupon purchased, as well as decrease the amount in the coupon.
     *
     * @param couponId
     * @param token    this is used to get the user type [included in tokenManager.getCustomerId()] and id.
     * @return
     * @throws CouponSysException
     */
    @Override
    public Coupon purchaseCoupon(int couponId, UUID token) throws CouponSysException {
        int customerId = tokenManager.getCustomerId(token);
        if (customerRepo.existsByCustomerIdAndCouponId(couponId, customerId))
            throw new CouponSysException(ExceptionType.ALREADY_PURCHASED);

        Coupon coupon1 = couponRepo.findById(couponId).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));

        if (coupon1.getAmount() <= 0) throw new CouponSysException(ExceptionType.OUT_OF_STOCK);
        if (coupon1.getEndDate().before(Date.valueOf(LocalDate.now())))
            throw new CouponSysException(ExceptionType.COUPON_EXPIRED);

        customerRepo.purchaseCoupon(customerId, couponId);

        coupon1.setAmount(coupon1.getAmount() - 1);
        return couponRepo.saveAndFlush(coupon1);
    }

    /**
     * @param token    includes our customerId, however the method calls tokenManager.getCustomerId() instead of getUserId(), this way if there isn't a match for a customer type in the token pool it will throw an exception.
     * @param category
     * @return
     * @throws CouponSysException
     */
    @Override
    public List<Coupon> getCustomerCouponsByCategory(UUID token, Category category) throws CouponSysException {
        return couponRepo.findCustomerCouponsByCategory(tokenManager.getCustomerId(token), category.name());
    }


    @Override
    public List<Coupon> getCustomerCouponsByMaxPrice(UUID token, double maxPrice) throws CouponSysException {
        return couponRepo.findCustomerCouponsPriceLessThan(tokenManager.getCustomerId(token), maxPrice);
    }

    @Override
    public List<Coupon> getCustomerCoupons(UUID token) throws CouponSysException {
        return couponRepo.findByCustomerId(tokenManager.getCustomerId(token));
    }

    @Override
    public List<Coupon> getAllCoupons(UUID token) throws CouponSysException {
        tokenManager.isAuthorized(token, ClientType.CUSTOMER);
        return couponRepo.findAll();
    }

    @Override
    public Customer getCustomerDetails(UUID token) throws CouponSysException {
        return customerRepo.findById(tokenManager.getCustomerId(token)).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));
    }


}
