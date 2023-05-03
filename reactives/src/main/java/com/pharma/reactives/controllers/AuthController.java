package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.RegistrationService;
import com.pharma.reactives.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Clasa AuthController este responsabila pentru gestionarea autentificarii si inregistrarii utilizatorilor.
 * @author Bodiu Dumitru
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator validator;
    private final RegistrationService registrationService;

    /**
     * Constructorul clasei AuthController
     * @param validator obiectul validator pentru validarea datelor utilizatorului
     * @param registrationService obiectul pentru inregistrarea unui utilizator nou
     */
    @Autowired
    public AuthController(PersonValidator validator, RegistrationService registrationService) {
        this.validator = validator;
        this.registrationService = registrationService;
    }

    /**
     * Metoda care returneaza pagina de autentificare a utilizatorului
     * @return pagina de autentificare
     */
    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    /**
     * Metoda care returneaza pagina de inregistrare a unui utilizator nou
     * @param person obiectul utilizatorului care va fi inregistrat
     * @return pagina de inregistrare
     */
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    /**
     * Metoda care efectueaza inregistrarea utilizatorului nou
     * @param person obiectul utilizatorului care va fi inregistrat
     * @param bindingResult rezultatul validarii datelor utilizatorului
     * @return pagina de inregistrare daca exista erori de validare, altfel redirecteaza catre pagina de autentificare
     */
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult){
        validator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "/auth/registration";
        person.setAccountNonLocked(true);
        registrationService.register(person);
        return "redirect:/auth/login";
    }
}
