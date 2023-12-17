package com.trading_company.trading_company.repositories;

import com.trading_company.trading_company.models.Shipment_List;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Shipment_ListRepository extends CrudRepository<Shipment_List, Integer> {
    List<Shipment_List> findByShipmentId(Integer id);
}
