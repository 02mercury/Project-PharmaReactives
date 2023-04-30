package com.pharma.reactives.services;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.repositories.MedicineRepository;
import com.pharma.reactives.repositories.ReactivesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MedicineService {
    private final ReactivesRepository reactivesRepository;
    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicineService(ReactivesRepository reactivesRepository, MedicineRepository medicineRepository) {
        this.reactivesRepository = reactivesRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> findAll(){
        return medicineRepository.findAll();
    }

    public Medicine findOne(int id){
        Optional<Medicine> foundMedicine = medicineRepository.findById(id);
        return foundMedicine.orElse(null);
    }

    @Transactional
    public void save(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    @Transactional
    public void update(int id, Medicine updatedMedicine){
        medicineRepository.findById(id).ifPresent(medicine -> {
            medicine.setPrice(updatedMedicine.getPrice());
            medicineRepository.save(medicine);
        });
    }

    @Transactional
    public void delete(int id){
        medicineRepository.deleteById(id);
    }

}
