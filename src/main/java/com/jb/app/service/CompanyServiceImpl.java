package com.jb.app.service;

import com.jb.app.beans.Category;
import com.jb.app.beans.Company;
import com.jb.app.beans.Coupon;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl extends ClientService implements CompanyService{

    public CompanyServiceImpl(CustomerRepo customerRepo, CompanyRepository companyRepo, CouponRepository couponRepo, TokenManager tokenManager) {
        super(customerRepo, companyRepo, couponRepo, tokenManager);
    }

    @Override
    public LoginResponse login(String email, String password) throws CouponSysException {
        Company result = companyRepo.findByEmailAndPassword(email, password).orElseThrow(()->new CouponSysException(ExceptionType.INVALID_EMAIL_AND_PASSWORD));
        Information information = Information.builder().id(result.getId()).email(email).clientType(ClientType.COMPANY).expirationTime(LocalDateTime.now().plusHours(30)).build();

        UUID token = tokenManager.addToken(information);
        return new LoginResponse(token,email, result.getId(), result.getName(), ClientType.COMPANY);
    }


    /**
     * this method makes sure the action requested for the coupon is allowed for that user
     * @param token
     * @param couponId
     * @throws CouponSysException
     */
    private void isCouponBelongToCompany(UUID token, int couponId) throws CouponSysException {
        if (!couponRepo.existsByIdAndCompanyId(couponId, tokenManager.getCompanyId(token))) throw new CouponSysException(ExceptionType.UNAUTHORIZED_ACTION);
    }

    /**
     * I chose not to check the start date's value (i.e. whether it was before the insertion of the coupon to the server) as I do not believe this is problematic and out of the question for it to happen once in a while not as an error. this is a they problem, not a me problem.
     * @param coupon
     * @param token
     * @throws CouponSysException
     */
    @Override
    public Coupon addCoupon(Coupon coupon, UUID token) throws CouponSysException {
        int companyId = tokenManager.getCompanyId(token);


        coupon.setCompany(companyRepo.findById(companyId).orElseThrow(()->new CouponSysException(ExceptionType.NO_SUCH_ID)));


        coupon.setId(0);

        if (coupon.getEndDate().before(coupon.getStartDate())) throw new CouponSysException(ExceptionType.INVALID_DATE);


        if (couponRepo.existsByNameAndCompanyId(coupon.getName(),companyId)) throw new CouponSysException(ExceptionType.COUPON_NAME_ALREADY_EXISTS);

        couponRepo.save(coupon);
        return coupon;
    }
    /**
     *
     *
     * @param token
     * @param coupon make sure that there are no empty fields.
     * @param couponId couponId is dangerous, if someone tries to change the title (coupon name) as they send the wrong couponId, and that title doesn't already exist (which it shouldn't) it will delete the existing coupon found at the id address sent. make sure to address this at the UI. todo
     * @throws CouponSysException
     */
    @Override
    public Coupon updateCoupon(UUID token, Coupon coupon, int couponId) throws CouponSysException {
        isCouponBelongToCompany(token,couponId);

        Coupon temp = couponRepo.findByIdAndCompanyId(couponId, coupon.getCompany().getId()).orElseThrow(()->new CouponSysException(ExceptionType.NO_SUCH_ID));

        if (!temp.getName().equalsIgnoreCase(coupon.getName()) && couponRepo.existsByNameAndCompanyId(coupon.getName(), couponId)) throw new CouponSysException(ExceptionType.COUPON_NAME_ALREADY_EXISTS);

        if (coupon.getEndDate().before(coupon.getStartDate())) throw new CouponSysException(ExceptionType.INVALID_DATE);


        coupon.setId(couponId);

        couponRepo.saveAndFlush(coupon);


        return coupon;
    }

    @Override
    public void deleteCoupon(UUID token, int couponId) throws CouponSysException {
        isCouponBelongToCompany(token,couponId);

        couponRepo.deletePurchasesByCouponId(couponId);
        couponRepo.deleteById(couponId);
    }

    public List<Coupon> getAllCoupons(UUID token) throws CouponSysException {
        return couponRepo.findByCompanyId(tokenManager.getCompanyId(token));
    }

    @Override
    public List<Coupon> getAllCouponsByCategory(Category category, UUID token) throws CouponSysException {
        return couponRepo.findByCompanyIdAndCategory(tokenManager.getCompanyId(token), category);
    }

    @Override
    public List<Coupon> getAllCouponsPriceLessThan(float price, UUID token) throws CouponSysException {
        return couponRepo.findByCompanyIdAndPriceLessThanEqualOrderById(price, tokenManager.getCompanyId(token));
    }

    @Override
    public Company getCompanyInfo(UUID token) throws CouponSysException {
        return companyRepo.findById(tokenManager.getCompanyId(token)).orElseThrow(() -> new CouponSysException(ExceptionType.NO_SUCH_ID));
    }

    @Override
    public Coupon getCoupon(UUID token, int couponId) throws CouponSysException {
        int companyId = tokenManager.getCompanyId(token);
        return couponRepo.findByIdAndCompanyId(couponId, companyId).orElseThrow(()->new CouponSysException(ExceptionType.NO_SUCH_ID));
    }


}
