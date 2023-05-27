package com.pharma.reactives.services;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.repositories.MedicineRepository;
import com.pharma.reactives.repositories.ReactivesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviciul MedicineService gestionează operațiile cu obiectele Medicine din baza de date.
 * Acesta utilizează repository-ul ReactivesRepository pentru a gestiona obiectele Reactive și repository-ul
 * MedicineRepository pentru a gestiona obiectele Medicine.
 * Serviciul utilizează adnotarea @Transactional pentru a se asigura că toate operațiile sunt realizate
 * într-un singur context de tranzacție.
 *
 * @author Bodiu Dumitru
 */
@Service
@Transactional(readOnly = true)
public class MedicineService {
    private final ReactivesRepository reactivesRepository;
    private final MedicineRepository medicineRepository;

    /**
     * Constructorul serviciului MedicineService.
     * @param reactivesRepository repository-ul ReactivesRepository pentru gestionarea obiectelor Reactive
     * @param medicineRepository repository-ul MedicineRepository pentru gestionarea obiectelor Medicine
     */
    @Autowired
    public MedicineService(ReactivesRepository reactivesRepository, MedicineRepository medicineRepository) {
        this.reactivesRepository = reactivesRepository;
        this.medicineRepository = medicineRepository;
    }

    /**
     * Metoda returnează o listă cu toate obiectele Medicine din baza de date.
     * @return o listă cu toate obiectele Medicine din baza de date
     */
    public List<Medicine> findAll(String keyword){
        if(keyword != null){
            return medicineRepository.findByKeyword(keyword.toUpperCase());
        }
        return medicineRepository.findAll();
    }

    /**
     * Metoda returnează obiectul Medicine cu id-ul specificat.
     * @param id id-ul obiectului Medicine căutat
     * @return obiectul Medicine cu id-ul specificat, sau null dacă obiectul nu există în baza de date
     */
    public Medicine findOne(int id){
        Optional<Medicine> foundMedicine = medicineRepository.findById(id);
        return foundMedicine.orElse(null);
    }

    /**
     * Metoda salvează un obiect Medicine în baza de date.
     * @param medicine obiectul Medicine de salvat în baza de date
     */
    @Transactional
    public void save(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    /**
     * Metoda actualizează obiectul Medicine cu id-ul specificat în baza de date.
     * @param id id-ul obiectului Medicine de actualizat
     * @param updatedMedicine obiectul Medicine cu noile date de actualizat
     */
    @Transactional
    public void update(int id, Medicine updatedMedicine){
        medicineRepository.findById(id).ifPresent(medicine -> {
            medicine.setPrice(updatedMedicine.getPrice());
            medicine.setDose(updatedMedicine.getDose());
            medicineRepository.save(medicine);
        });
    }
    /**
     * Metoda șterge obiectul Medicine cu id-ul specificat din baza de date.
     * @param id id-ul obiectului Medicine de șters
     */
    @Transactional
    public void delete(int id){
        medicineRepository.deleteById(id);
    }

    public int total(){
        return medicineRepository.total();
    }
}
