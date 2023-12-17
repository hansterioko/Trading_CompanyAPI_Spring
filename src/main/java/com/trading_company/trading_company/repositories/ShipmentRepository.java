package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Shipment;
import org.springframework.data.repository.CrudRepository;

public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
}
