package com.trading_company.trading_company.dtos;

import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.models.Shipment;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment_ListDTO {
    private Integer id;

    private Integer price;

    private Integer count;

    private Shipment shipment;

    private ProductDTO product;
}
