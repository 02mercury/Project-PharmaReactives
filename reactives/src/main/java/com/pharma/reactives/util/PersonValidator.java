package com.pharma.reactives.util;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.security.auth.login.AccountLockedException;

/**
 * Aceasta clasa este un validator pentru clasa Person si se asigura ca nu sunt validate mai multe
 * persoane cu acelasi username.
 *
 * @author Bodiu Dumitru
 */
@Component
public class PersonValidator implements Validator {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Construieste un nou validator pentru clasa Person, care verifica daca exista deja
     * un utilizator cu acelasi nume de utilizator.
     *
     * @param userDetailsService un obiect CustomUserDetailsService,
     * care este utilizat pentru a verifica daca exista deja un utilizator cu numele dat
     */
    public PersonValidator(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Verifica daca clasa data ca parametru este o instanta a clasei Person.
     * @param aClass clasa de verificat
     * @return true daca este o instanta a clasei Person, altfel false
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    /**
     * Verifica daca exista deja un utilizator cu acelasi nume de utilizator.
     * @param target obiectul care trebuie validat
     * @param errors obiectul care contine erorile
     */
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
