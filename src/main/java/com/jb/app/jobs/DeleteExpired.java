package com.jb.app.jobs;

import com.jb.app.repo.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DeleteExpired {
    private final CouponRepository couponRepo;
    private final int rate = 30*60*24;
    @Scheduled(fixedRate = 2_000 * rate, initialDelay = 1000)
    public void removeExpiredCoupons(){
        couponRepo.deleteExpiredPurchases();
        couponRepo.deleteExpiredCoupons();
    }
}
