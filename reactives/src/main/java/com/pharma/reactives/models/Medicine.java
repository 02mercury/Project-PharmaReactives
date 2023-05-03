package com.pharma.reactives.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clasa care reprezinta entitatea Medicament.
 * Contine proprietati precum: id, nume, doza, pret si o referinta la obiectul Reactive.
 *
 * @author Bodiu Dumitru
 */
@Data
@Entity
@Table(name = "medicines")
public class Medicine {
    /**
     * Campul id este cheia primara a tabelului si este generata automat de baza de date.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Numele medicamentului, acesta trebuie sa fie intre 2 si 30 de caractere.
     */
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    /**
     * Doza medicamentului, aceasta trebuie sa fie mai mare decat 0mg.
     */
    @Min(value = 0, message = "Dose should be grater than 0mg")
    @Column(name = "dose")
    private double dose;

    /**
     * Pretul medicamentului, acesta trebuie sa fie mai mare decat 0$.
     */
    @Min(value = 0, message = "Price should be grater than 0$")
    @Column(name = "price")
    private double price;

    /**
     * Obiectul Reactive la care se leaga acest medicament.
     */
    @ManyToOne
    @JoinColumn(name = "id_reactive", referencedColumnName = "id")
    private Reactive reactive;

    /**
     * Constructorul default al clasei Medicine. Doza si pretul sunt initializate cu 0.
     */
    public Medicine(){
        this.dose = 0;
        this.price = 0;
    }
}
