package com.pharma.reactives.util;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.security.auth.login.AccountLockedException;

@Component
public class PersonValidator implements Validator {

    private final CustomUserDetailsService userDetailsService;

    public PersonValidator(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;

        try{
            userDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // totul e ok, userul nu este gasit
        }

        errors.rejectValue("username", "", "User \"" + person.getUsername() + "\" already exists");
    }
}
