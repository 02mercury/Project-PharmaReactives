package com.pharma.reactives.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="reactives")
public class Reactive {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "formula")
    private String formula;

    @Column(name = "stock")
    private double stock;

    @Column(name = "price")
    private double price;

    @Column(name = "measure_unit")
    @Enumerated(EnumType.STRING)
    private Unit measure_unit;

    public Reactive(){
        this.stock = 0;
        this.price = 0;
        this.measure_unit = Unit.KG;
    }

    public Reactive(String name, String formula){
        this.name = name;
        this.formula = formula;
    }

    public Reactive(double stock, double price, Unit measure_unit){
        this.stock = stock;
        this.price = price;
        this.measure_unit = measure_unit;
    }

}
