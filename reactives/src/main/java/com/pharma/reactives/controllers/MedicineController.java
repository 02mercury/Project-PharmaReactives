package com.pharma.reactives.controllers;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.MedicineService;
import com.pharma.reactives.services.ReactiveService;
import com.pharma.reactives.util.MedicineValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Clasa MedicineController gestioneaza requesturile primite pentru entitatea "medicamente"
 * si le redirecționeaza catre serviciile aferente.

 @author Bodiu Dumitru
 */
@Slf4j
@Controller
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineService medicineService;
    private final ReactiveService reactiveService;
    private final MedicineValidator medicineValidator;

    /**
     * Constructorul MedicineController initializeaza serviciile de care are nevoie clasa.
     *
     * @param medicineService - serviciu pentru entitatea "medicines"
     * @param reactiveService - serviciu pentru entitatea "reactives"
     * @param medicineValidator - validator pentru entitatea "medicines"
     */
    @Autowired
    public MedicineController(MedicineService medicineService, ReactiveService reactiveService, MedicineValidator medicineValidator) {
        this.medicineService = medicineService;
        this.reactiveService = reactiveService;
        this.medicineValidator = medicineValidator;
    }

    /**
     * Aceasta metoda returneaza toate medicamentele din baza de date.
     *
     * @param model - model pentru a transmite datele medicamentelor la view-ul corespunzator
     * @return String - numele view-ului corespunzator listei de medicamente
     */
    @GetMapping()
    public String getAll(Model model, Authentication authentication){
        model.addAttribute("medicines", medicineService.findAll());
        model.addAttribute("authentication", authentication);
        return "medicines/index";
    }

    /**
     * Aceasta metoda returneaza medicamentul cu id-ul specificat.
     *
     * @param id - id-ul medicamentului cautat
     * @param model - model pentru a transmite datele medicamentului la view-ul corespunzator
     * @return String - numele view-ului corespunzator afisarii unui medicament
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model,
                          Authentication authentication){
        Medicine medicine = medicineService.findOne(id);
        int quantity = (int) (medicine.getReactive().getStock() / medicine.getDose());

        model.addAttribute("medicine", medicineService.findOne(id));
        model.addAttribute("cartItem", new CartItem());
        model.addAttribute("authentication", authentication);
        model.addAttribute("quantity", quantity);
        return "medicines/show";
    }

    /**
     * Aceasta metoda returneaza formularul de creare a unui nou medicament.
     *
     * @param model - model pentru a transmite datele necesare crearii unui medicament la view-ul corespunzator
     * @return String - numele view-ului corespunzator crearii unui medicament
     */
    @GetMapping("/new")
    public String newMedicine(Model model,
                              Authentication authentication){

        model.addAttribute("medicine", new Medicine());
        model.addAttribute("reactiveList", reactiveService.findAll());
        model.addAttribute("authentication", authentication);
        return "medicines/new";
    }

    /**
     * Aceasta metoda salveaza un nou medicament in baza de date.
     *
     * @param medicine - obiectul "medicament" creat in urma submit-ului formularului de creare a unui medicament
     * @param bindingResult - rezultatul validarii obiectului "medicament"
     * @param model - model pentru a transmite datele necesare crearii unui medicament la view-ul corespunzator
     * @return String - redirectionare catre lista de medicamente daca medicamentul a fost creat cu succes,
     * in caz contrar -> catre pagina de creare a medicamentului
     */
    @PostMapping()
    public String create(@ModelAttribute("medicine") @Valid Medicine medicine,
                         BindingResult bindingResult,
                         Authentication authentication,
                         Model model){
        Reactive reactive = reactiveService.findOne(medicine.getReactive().getId());

        medicineValidator.validate(medicine, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("reactiveList", reactiveService.findAll());
            model.addAttribute("authentication", authentication);
            return "/medicines/new";
        }

        medicine.setReactive(reactive);
        medicineService.save(medicine);

        return "redirect:/medicines";
    }

    /**
     * Această metodă primește cererea HTTP GET pentru a edita un medicament și
     * returnează pagina de editare.
     *
     * @param id - identificatorul unic al medicamentului de editat
     * @param model - modelul folosit pentru a furniza datele medicamentului de editat
     * @return String - pagina de editare a medicamentului
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model,
                       Authentication authentication){
        model.addAttribute("authentication", authentication);
        model.addAttribute("medicine", medicineService.findOne(id));
        return "medicines/edit";
    }

    /**
     * Această metodă primește cererea HTTP PATCH pentru a actualiza un medicament și
     * returnează pagina cu toate medicamentele disponibile.
     *
     * @param medicine - obiectul de tip Medicine care trebuie actualizat
     * @param id - identificatorul unic al medicamentului care trebuie actualizat
     * @return String - pagina cu toate medicamentele disponibile
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("medicine") Medicine medicine,
                         @PathVariable("id") int id,
                         BindingResult bindingResult,
                         Authentication authentication,
                         Model model){
        medicine.setReactive(medicineService.findOne(id).getReactive());

        medicineValidator.validate(medicine, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("authentication", authentication);
            return "/medicines/edit";
        }

        medicineService.update(id, medicine);



        return "redirect:/medicines";
    }

    /**
     * Această metodă primește cererea HTTP DELETE pentru a șterge un medicament și
     * returnează pagina cu toate medicamentele disponibile.
     *
     * @param id - identificatorul unic al medicamentului care trebuie sters
     * @return String - pagina cu toate medicamentele disponibile
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        medicineService.delete(id);
        return "redirect:/medicines";
    }
}
