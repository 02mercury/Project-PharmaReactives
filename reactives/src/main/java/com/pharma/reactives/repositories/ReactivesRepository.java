package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Reactive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Aceasta este o interfata care extinde JpaRepository si se ocupa de accesarea si
 * manipularea entitatii "reactives" din baza de date.
 * JpaRepository ofera metode generice pentru crearea, citirea, actualizarea si stergerea obiectelor.
 *
 * @author Bodiu Dumitru
 */
@Repository
public interface ReactivesRepository extends JpaRepository<Reactive, Integer> {
    @Query("SELECT COUNT(*) FROM Reactive r")
    int total();

    @Query("SELECT r FROM Reactive r WHERE "
            + "UPPER(CONCAT(r.id,  ' ', r.name,  ' ', r.price,  ' ', r.formula,  ' ', r.stock)) "
            + "LIKE %?1%")
    Page<Reactive> findAll(String keyword, Pageable pageable);
}
