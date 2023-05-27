package com.pharma.reactives.util;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReactiveValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Reactive.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reactive reactive = (Reactive)target;

        if(reactive.getPrice() < 0.01){
            errors.rejectValue("price", "", "Price should be grater than 0.01 mdl");
        }

        if(reactive.getStock() < 1){
            errors.rejectValue("stock", "", "Stock should be grater than 1 mg");
        }
    }
}
