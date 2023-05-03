package com.pharma.reactives.util;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.ReactiveService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Aceasta clasa reprezinta un validator pentru entitatea Medicine.
 * Verifica daca doza medicamentului este mai mare decat cantitatea disponibila in stocul
 * substantei reactive corespunzatoare.
 *
 * @author Bodiu Dumitru
 */
@Component
public class MedicineValidator implements Validator {
    private final ReactiveService reactiveService;

    /**
     * Constructorul clasei MedicineValidator.
     * @param reactiveService serviciul pentru gestionarea substantelor reactive
     */
    public MedicineValidator(ReactiveService reactiveService) {
        this.reactiveService = reactiveService;
    }

    /**
     * Verifica daca clasa de entitati este aceeasi cu clasa Medicine.
     * @param aClass clasa care trebuie verificata
     * @return true daca este aceeasi clasa, false in caz contrar
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Medicine.class.equals(aClass);
    }

    /**
     * Valideaza daca doza medicamentului este mai mica sau egala cu cantitatea disponibila in stocul
     * substantei reactive corespunzatoare.
     *
     * @param target entitatea care trebuie validata
     * @param errors erorile generate in urma validarii
     */
    @Override
    public void validate(Object target, Errors errors) {
        Medicine medicine = (Medicine)target;
        Reactive reactive = reactiveService.findOne(medicine.getReactive().getId());
        if(reactive == null || medicine.getDose() > reactive.getStock()){
            errors.rejectValue("dose", "", "Dose is greater than available stock. Max value is " +
                    Objects.requireNonNull(reactive).getStock() + "mg");
        }
    }
}
