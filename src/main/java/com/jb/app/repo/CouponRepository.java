package com.jb.app.repo;

import com.jb.app.beans.Category;
import com.jb.app.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where customer_id = ?1", nativeQuery = true)
    void deletePurchasesByCustomerId(int customerId);

    boolean existsByIdAndCompanyId(int id, int companyId);

    List<Coupon> findByCompanyId(int companyId);

    @Query(value = "SELECT * FROM coupons inner join customers_coupons on coupons_id = id where customer_id = ?;", nativeQuery = true)
    List<Coupon> findByCustomerId(int customerId);

    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    List<Coupon> findByCompanyIdAndPriceLessThan(int companyId, double maxPrice);

    List<Coupon> findByCompanyIdAndPriceLessThanEqualOrderById(float price, int companyId);

    boolean existsByNameAndCompanyId(String name, int companyId);

    boolean existsByName(String name);

    Optional<Coupon> findByIdAndCompanyId(int id, int companyId);

    @Query(value = "SELECT * FROM coupons inner join customers_coupons on coupons_id = id where customer_id = ? and category = ?;", nativeQuery = true)
    List<Coupon> findCustomerCouponsByCategory(int customerId, String category);

    @Query(value = "SELECT * FROM coupons inner join customers_coupons on coupons_id = id where customer_id = ? and price <= ?;", nativeQuery = true)
    List<Coupon> findCustomerCouponsPriceLessThan(int customerId, double maxPrice);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE customers_coupons.coupons_id = ?1", nativeQuery = true)
    void deletePurchasesByCouponId(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_id IN "
            + "(SELECT id FROM coupons WHERE company_id = ?1)", nativeQuery = true)
    void deletePurchasesByCompanyId(int companyId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE customers_coupons.coupons_id IN(SELECT id FROM coupons WHERE end_date < CURDATE())", nativeQuery = true)
    void deleteExpiredPurchases();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE end_date < CURDATE()", nativeQuery = true)
    void deleteExpiredCoupons();

    /*@Transactional
    @Modifying
    @Query(value = "DELETE `caasii`.`customers_coupons`, `caasii`.`coupons` " +
            "FROM `caasii`.`customers_coupons` " +
            "INNER JOIN  `caasii`.`coupons` " +
            "ON  `customers_coupons`.`coupons_id` = `coupons`.`id` " +
            "WHERE (end_date < curdate()); ", nativeQuery = true)
    void dailyRemoveJob1(); //
*/

    /**/

    /*

    @Transactional
    @Modifying
    @Query(value = "delete FROM `caasii`.`coupons` WHERE end_date < curdate(); ", nativeQuery = true)
    void dailyRemoveJob2();

    @Transactional
    @Modifying
    @Query(value = "set SQL_SAFE_UPDATES = 0; ", nativeQuery = true)
    void enableUpdates();
    @Transactional
    @Modifying
    @Query(value = "set SQL_SAFE_UPDATES = 1; ", nativeQuery = true)
    void disableUpdates();*/
}


//@Query("from Coupon where end_date + 1 < current_date")
//	List<Coupon> getExpiredCoupons();

