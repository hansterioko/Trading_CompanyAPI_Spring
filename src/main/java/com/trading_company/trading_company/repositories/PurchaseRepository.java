package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
}
