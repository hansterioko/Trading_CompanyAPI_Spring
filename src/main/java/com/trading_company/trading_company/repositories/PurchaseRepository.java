package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Purchase;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    List<Purchase> findAll(Sort id);
    List<Purchase> findAllByOrderByDateDesc();
}
