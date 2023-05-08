package com.pharma.reactives.util;

import com.pharma.reactives.models.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;
        String ccNumber = order.getCcNumber();

        if (!isValidCreditCardNumber(ccNumber)) {
            errors.rejectValue("ccNumber", "","Not a valid credit card number");
        }
    }

    private boolean isValidCreditCardNumber(String ccNumber){
        // Implementarea regulilor de validare a numarului de card
        // Exemplu: numarul de card trebuie sa aiba 16 cifre si sa inceapa cu 4 sau 5
        return ccNumber.matches("^4[0-9]{15}$|^5[1-5][0-9]{14}$");
    }
}
