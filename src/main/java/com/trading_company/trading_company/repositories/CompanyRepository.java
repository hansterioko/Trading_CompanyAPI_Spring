package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Company findByName(String name);
}
