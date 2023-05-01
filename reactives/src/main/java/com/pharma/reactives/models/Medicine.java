package com.pharma.reactives.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Dose should be grater than 0mg")
    @Column(name = "dose")
    private double dose;

    @Min(value = 0, message = "Price should be grater than 0$")
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
