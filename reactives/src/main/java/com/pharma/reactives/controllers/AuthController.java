package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.RegistrationService;
import com.pharma.reactives.util.PersonValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator validator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator validator, RegistrationService registrationService) {
        this.validator = validator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

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
