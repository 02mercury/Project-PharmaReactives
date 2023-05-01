package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.MedicineService;
import com.pharma.reactives.services.ReactiveService;
import com.pharma.reactives.util.MedicineValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineService medicineService;
    private final ReactiveService reactiveService;
    private final MedicineValidator medicineValidator;

    @Autowired
    public MedicineController(MedicineService medicineService, ReactiveService reactiveService, MedicineValidator medicineValidator) {
        this.medicineService = medicineService;
        this.reactiveService = reactiveService;
        this.medicineValidator = medicineValidator;
    }

    @GetMapping()
    public String getAll(Model model){
        model.addAttribute("medicines", medicineService.findAll());
        return "medicines/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model){
        model.addAttribute("medicine", medicineService.findOne(id));
        return "medicines/show";
    }

    @GetMapping("/new")
    public String newMedicine(Model model){

        model.addAttribute("medicine", new Medicine());
        // transmit o lista de reactive, pentru a le afisa pe pagina web
        model.addAttribute("reactiveList", reactiveService.findAll());
        return "medicines/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("medicine") @Valid Medicine medicine,
                         BindingResult bindingResult,
                         Model model){
        // Creez un obiect in care se va pastra informatia primita
        Reactive reactive = reactiveService.findOne(medicine.getReactive().getId());

        // validarea dozei introduse de catre utilizator
        medicineValidator.validate(medicine, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("reactiveList", reactiveService.findAll());
            return "/medicines/new";
        }

        // Fac update la stock
        reactive.setStock(reactive.getStock() - medicine.getDose());
        reactiveService.update(reactive.getId(), reactive);
        // Atribui reactivul pentru medicament
        medicine.setReactive(reactive);
        // salvez reactivul
        medicineService.save(medicine);

        return "redirect:/medicines";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("medicine", medicineService.findOne(id));
        return "medicines/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("medicine") Medicine medicine,
                         @PathVariable("id") int id){
        medicineService.update(id, medicine);
        return "redirect:/medicines";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        medicineService.delete(id);
        return "redirect:/medicines";
    }
}
