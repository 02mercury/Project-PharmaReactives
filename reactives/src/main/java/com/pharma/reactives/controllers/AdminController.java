package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Clasa AdminController este un controller pentru gestionarea
 * conturilor de utilizatori cu rol de administrator.
 *
 * Aceasta clasa este responsabila pentru a face legatura intre
 * view-uri si serviciul care se ocupa cu logica de business a aplicatiei.
 * @author : Bodiu Dumitru
 */
@Controller
@RequestMapping("/accounts")
public class AdminController {

    private final AccountService accountService;

    /**
     * Constructor care utilizeaza anotarea @Autowired
     * pentru a injecta instanta de AccountService
     * @param accountService
     */
    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Metoda care intoarce o lista cu toti utilizatorii si o afiseaza in pagina de index a conturilor.
     * @param model - modelul de date care va fi trimis catre view
     * @return - pagina index a conturilor
     */
    @GetMapping
    public String getAll(Model model){
        model.addAttribute("users", accountService.findAll());
        return "accounts/index";
    }

    /**
     * Metoda care intoarce un utilizator specific in functie de id-ul dat ca parametru si afiseaza informatiile acestuia.
     * @param id - id-ul utilizatorului cautat
     * @param model - modelul de date care va fi trimis catre view
     * @return - pagina de afisare a utilizatorului
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model){
        model.addAttribute("user", accountService.findOne(id));
        return "accounts/show";
    }

    /**
     * Metoda care afiseaza pagina de editare pentru un utilizator specific in functie de id-ul dat ca parametru.
     * @param id - id-ul utilizatorului care trebuie editat
     * @param model - modelul de date care va fi trimis catre view
     * @return - pagina de editare a utilizatorului
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("user", accountService.findOne(id));
        return "accounts/edit";
    }

    /**
     * Metoda care actualizeaza datele unui utilizator specific in functie de id-ul dat ca parametru.
     * @param person - obiectul care contine noile informatii ale utilizatorului
     * @param id - id-ul utilizatorului care trebuie actualizat
     * @return - pagina de index a conturilor
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Person person,
                         @PathVariable("id") int id){
        accountService.update(id, person);
        return "redirect:/accounts";
    }

    /**
     * Metoda care sterge un utilizator specific in functie de id-ul dat ca parametru.
     * @param id - id-ul utilizatorului care trebuie sters
     * @return - pagina de index a conturilor
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        accountService.delete(id);
        return "redirect:/accounts";
    }
}
