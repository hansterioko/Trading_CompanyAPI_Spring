package com.trading_company.trading_company.dtos;

import com.trading_company.trading_company.models.Company;
import com.trading_company.trading_company.models.Purchase_List;
import com.trading_company.trading_company.models.Shipment_List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private int vat;
    private String category;
    private String characteristic;
    private String Unit;
    private Integer price;
    private Integer countOnWarehouse;
    private byte[] photo;
    private Company company;
}
