package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Aceasta este o interfata care extinde JpaRepository si se ocupa de accesarea si
 * manipularea entitatii "medicines" din baza de date.
 * JpaRepository ofera metode generice pentru crearea, citirea, actualizarea si stergerea obiectelor.
 *
 * @author Bodiu Dumitru
 */
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    @Query("SELECT m FROM Medicine  m WHERE " +
            " UPPER(CONCAT(m.id, m.name, m.reactive.name, m.price)) " +
            " LIKE %?1% ")
    List<Medicine> findByKeyword(String keyword);
}
