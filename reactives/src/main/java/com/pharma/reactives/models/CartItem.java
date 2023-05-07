package com.pharma.reactives.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_medicine", referencedColumnName = "id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Person user;

    private int quantity;

    @Transient
    public double getSubTotal(){
        return this.medicine.getPrice() * quantity;
    }
}
