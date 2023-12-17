package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Purchase_List;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Purchase_ListRepository extends CrudRepository<Purchase_List, Integer> {
    List<Purchase_List> findByPurchaseId(int id);
}
