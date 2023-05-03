package com.pharma.reactives.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Clasa Reactive este utilizată pentru a stoca informații despre un reactiv chimic și se mapează la tabela "reactives" din baza de date.
 * Această clasă include câmpurile id, name, formula, stock și price.
 * De asemenea, clasa include o listă de medicamente care conțin acest reactiv.
 *
 * @author Bodiu Dumitru
 */
@Data
@Entity
@Table(name="reactives")
public class Reactive {
    /**
     * Campul id este cheia primara a tabelului si este generata automat de baza de date.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Numele reactivului.
     */
    @Column(name = "name")
    private String name;

    /**
     * Formula chimică a reactivului.
     */
    @Column(name = "formula")
    private String formula;

    /**
     * Stocul disponibil al reactivului.
     */
    @Column(name = "stock")
    private double stock;

    /**
     * Prețul unitar al reactivului.
     */
    @Column(name = "price")
    private double price;

    /**
     * Constructor implicit al clasei Reactive. Inițializează stocul și prețul cu 0.
     */
    public Reactive(){
        this.stock = 0;
        this.price = 0;
    }

    /**
     * Constructor pentru a crea un reactiv cu numele și formula specificate.
     *
     * @param name Numele reactivului.
     * @param formula Formula chimică a reactivului.
     */
    public Reactive(String name, String formula){
        this.name = name;
        this.formula = formula;
    }

    /**
     * Constructor pentru a crea un reactiv cu stocul și prețul specificate.
     *
     * @param stock Stocul disponibil al reactivului.
     * @param price Prețul unitar al reactivului.
     */
    public Reactive(double stock, double price){
        this.stock = stock;
        this.price = price;
    }

    /**
     * Lista de medicamente care conțin acest reactiv.
     */
    @OneToMany(mappedBy = "reactive", cascade = CascadeType.ALL)
    private List<Medicine> medicines;
}
