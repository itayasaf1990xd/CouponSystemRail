package com.jb.app.repo;

import com.jb.app.beans.Coupon;
import com.jb.app.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {


    Optional<Customer> findByEmailAndPassword(String email, String password);

    Customer findByEmail(String email);

    //boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM coupons inner join customers_coupons ON coupons_id = ?1 AND customer_id = ?2) THEN 'true' ELSE 'false' END", nativeQuery = true)
    boolean existsByCustomerIdAndCouponId(int couponId, int customerId);

    boolean existsByEmailAndId(String email, int id);

    @Transactional
    @Modifying
    @Query(value = "Insert into customers_coupons (customer_id,coupons_id) values (?1,?2)", nativeQuery = true)
    void purchaseCoupon(int customerId, int couponId);

    List<Coupon> getCouponsById(int id);

    //List<Coupon> getCouponsByIdAndCategory();

    //int getCustomerIdByEmailAndPassword(String email, String password);
}
