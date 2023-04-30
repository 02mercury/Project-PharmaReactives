package com.pharma.reactives.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public Reactive(){
        this.stock = 0;
        this.price = 0;
    }

    public Reactive(String name, String formula){
        this.name = name;
        this.formula = formula;
    }

    public Reactive(double stock, double price){
        this.stock = stock;
        this.price = price;
    }

    @OneToMany(mappedBy = "reactive", cascade = CascadeType.ALL)
    private List<Medicine> medicines;

}
