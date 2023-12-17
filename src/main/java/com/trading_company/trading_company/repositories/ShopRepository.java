package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Integer> {
    Shop findByName(String name);
}
