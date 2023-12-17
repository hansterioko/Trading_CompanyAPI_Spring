package com.trading_company.trading_company.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Стоимость поставки не подсчитана")
    private Integer price;

    @Min(value = 0, message = "Скидка не может быть меньше 0%")
    @Max(value = 99, message = "Скидка не может быть более 99%")
    private Integer discount;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<Shipment_List> shipmentList;

    @Column(nullable = false)
    private LocalDate date;
}
