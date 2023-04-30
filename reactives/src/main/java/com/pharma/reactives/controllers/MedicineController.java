package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.MedicineService;
import com.pharma.reactives.services.ReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineService medicineService;
    private final ReactiveService reactiveService;

    @Autowired
    public MedicineController(MedicineService medicineService, ReactiveService reactiveService) {
        this.medicineService = medicineService;
        this.reactiveService = reactiveService;
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
        List<Reactive> reactiveList = reactiveService.findAll();
        model.addAttribute("medicine", new Medicine());
        // transmit o lista de reactive, pentru a le afisa pe pagina web
        model.addAttribute("reactiveList", reactiveList);
        return "medicines/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("medicine") Medicine medicine){
        // Creez un obiect in care se va pastra informatia primita
        Reactive reactive = reactiveService.findOne(medicine.getReactive().getId());

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
