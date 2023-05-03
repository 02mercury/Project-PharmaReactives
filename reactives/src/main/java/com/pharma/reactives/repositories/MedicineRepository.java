package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Aceasta este o interfata care extinde JpaRepository si se ocupa de accesarea si
 * manipularea entitatii "medicines" din baza de date.
 * JpaRepository ofera metode generice pentru crearea, citirea, actualizarea si stergerea obiectelor.
 *
 * @author Bodiu Dumitru
 */
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
