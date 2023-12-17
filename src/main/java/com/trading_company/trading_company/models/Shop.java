package com.trading_company.trading_company.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Введите название магазина")
    private String name;
    @NotBlank(message = "Введите название города")
    private String city;
    @NotBlank(message = "Введите название улицы")
    private String street;
    @NotBlank(message = "Введите номер дома")
    private String house;
}
