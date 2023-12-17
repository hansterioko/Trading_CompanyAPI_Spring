package com.trading_company.trading_company.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Необходимо ввести название")
    private String name;
    @Positive
    @Min(value = 10, message = "Минимальный НДС 10%")
    @Min(value = 20, message = "Максимальный НДС 20%")
    private int vat;
    @NotBlank(message = "Укажите категорию товара")
    private String category;
    @NotBlank(message = "Напишите характеристику товару")
    private String characteristic;
    @NotBlank(message = "Укажите единицу измерения для товара")
    private String Unit;
    private Integer price;
    private Integer countOnWarehouse;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Shipment_List> shipmentList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Purchase_List> purchaseList;
}
