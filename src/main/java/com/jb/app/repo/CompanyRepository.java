package com.jb.app.repo;

import com.jb.app.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    boolean existsByName(String name);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByNameOrEmailOrId(String name, String email, int id);

    boolean existsByEmail(String email);

    boolean existsByIdAndName(int id, String name);

    Optional<Company> findByEmailAndPassword(String email, String password);

    Company findByEmail(String email);

    Company findByName(String name);

}
