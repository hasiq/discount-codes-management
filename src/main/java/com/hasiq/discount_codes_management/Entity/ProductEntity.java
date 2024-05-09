package com.hasiq.discount_codes_management.Entity;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    private String description;

    private String currency;


    public ProductEntity(String name, Double price, String description, String currency) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.currency = currency;
    }
}
