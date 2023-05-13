package com.pharma.reactives.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Clasa Person este o entitate JPA care reprezintă un utilizator al aplicației.
 * Fiecare instanță a acestei clase este salvată în tabela "users" din baza de date.
 * Clasa include câmpuri pentru id, username, data nașterii, parola, rolul utilizatorului și starea contului (locked/unlocked).
 * De asemenea, aceasta include construcțori, setteri și getteri pentru fiecare câmp.
 *
 * @author Bodiu Dumitru
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Person {
    /**
     * Campul id este cheia primara a tabelului si este generata automat de baza de date.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Campul username este numele utilizatorului si este obligatoriu sa fie intre 2 si 100 de caractere.
     */
    @NotNull
    @Size(min = 2, max = 100, message = "The Username must be between 2 and 100 characters")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Campul date_of_birth este data de nastere a utilizatorului si nu poate fi null.
     */
    @NotNull(message = "The Date of Birth cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false)
    private Date date_of_birth;

    /**
     * Campul password este parola utilizatorului si trebuie sa respecte un anumit format de validare.
     */
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password policy")
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Campul role reprezinta rolul utilizatorului si este reprezentat de o enumeratie.
     */
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Campul accountNonLocked indica daca contul utilizatorului este blocat sau nu.
     */
    @Column(name = "accountNonLocked")
    private boolean accountNonLocked;


}
