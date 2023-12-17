package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByCompanyId(Integer id);
}
