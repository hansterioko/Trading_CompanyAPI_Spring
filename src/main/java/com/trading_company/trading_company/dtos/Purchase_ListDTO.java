package com.trading_company.trading_company.dtos;

import com.trading_company.trading_company.models.Product;
import com.trading_company.trading_company.models.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase_ListDTO {
    private Integer id;
    private Integer count;
    private Purchase purchase;
    private ProductDTO product;
}
