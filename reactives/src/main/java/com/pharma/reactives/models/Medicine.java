package com.pharma.reactives.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dose")
    private double dose;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "id_reactive", referencedColumnName = "id")
    private Reactive reactive;

    public Medicine(){
        this.dose = 0;
        this.price = 0;
    }
}
