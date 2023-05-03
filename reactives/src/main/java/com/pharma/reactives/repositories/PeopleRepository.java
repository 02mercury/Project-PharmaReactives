package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Aceasta este o interfata care extinde JpaRepository si se ocupa de accesarea si
 * manipularea entitatii "users" din baza de date.
 * JpaRepository ofera metode generice pentru crearea, citirea, actualizarea si stergerea obiectelor.
 *
 * @author Bodiu Dumitru
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);
}
