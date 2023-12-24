package com.trading_company.trading_company.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shipment_list")
public class Shipment_List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Цена поставки не подсчитана")
    private Integer price;

    @Min(value = 1, message = "Кол-во товара в поставке не может быть меньше 1")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
