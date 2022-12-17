package com.jb.app.test;

import com.jb.app.beans.Company;
import com.jb.app.beans.Customer;
import com.jb.app.exceptions.CouponSysException;
import com.jb.app.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

//@Component
@RequiredArgsConstructor
//@Order(2)
public class AdminServiceTests implements CommandLineRunner {

    private final AdminService adminService;



    @Override
    public void run(String... args) throws Exception {
        /*adminLoginTest1();
        addCompanyTest();
        updateCompanyTest();
        deleteCompanyTest();
        addCustomerTest();*/
    }
    /*

    private void adminLoginTest1(){
        System.out.println("expected fail");
//        System.out.println(adminService.login("sdf","asd"));
        System.out.println("expected success");
//        System.out.println(adminService.login("admin@admin.com", "admin"));

    }

    private void addCompanyTest() throws CouponSysException {
        Company c1 = Company.builder()
                .name("yes")
                .email("yes@yes.com")
                .password("yes")
                .build();
        adminService.addCompany(c1, );
    }

    private void updateCompanyTest() throws CouponSysException {
        Company company = adminService.getOneCompany(2);
        company.setPassword("234");
//        adminService.updateCompany(, company);
        company.setName("diznutz");

        //adminService.updateCompany(company);
    }

    private void deleteCompanyTest() throws CouponSysException {
        adminService.deleteCompany(2);
        System.out.println("expecting one company id = 1");
        adminService.getAllCompanies().forEach(System.out::println);
        System.out.println("------------------------------------");
    }

    private void addCustomerTest() throws CouponSysException {
        Customer cr1 = Customer.builder()
                .firstName("moshe")
                .lastName("shasha")
                .email("moshe@gmail.com")
                .password("1234")
                .build();
        adminService.addCustomer(cr1);
        //adminService.addCustomer(cr1);
        System.out.println(adminService.getOneCustomer(2));
    }

    private void updateCustomerTest(){

    }

    private void deleteCustomerTest(){

    }*/
}
