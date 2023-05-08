package com.pharma.reactives.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
public class Order {
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

    private double price;

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{4}", message = "Your address sould be in this format: Country, City, Postal Code (4 digits)")
    @Column(name = "delivery_address")
    private String delivery_address;

    @Column(name = "cc_number")
    private String ccNumber;



    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$",
            message = "Must be formatted MM/YY")
    @Column(name = "cc_expiration")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @Column(name = "placed_at")
    private Timestamp placed_at;
}
