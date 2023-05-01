package com.pharma.reactives.util;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.ReactiveService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class MedicineValidator implements Validator {
    private final ReactiveService reactiveService;

    public MedicineValidator(ReactiveService reactiveService) {
        this.reactiveService = reactiveService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Medicine.class.equals(aClass);
    }

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
