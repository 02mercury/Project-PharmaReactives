package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.ReactiveService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clasa Java pentru gestionarea operatiunilor CRUD pentru entitatea "Reactives".
 * Acesta utilizeaza Spring MVC si ReactiveService pentru a interactiona cu baza de date.
 * Aceasta clasa contine metode HTTP pentru a obtine, crea, actualiza si sterge date despre Reactives.
 *
 * @author Bodiu Dumitru
 */
@Slf4j
@Controller
@RequestMapping("/reactives")
public class ReactivesController {
    private final ReactiveService reactiveService;

    /**
     * Constructor pentru clasa ReactivesController.
     *
     * @param reactiveService - serviciu care ofera metodele ReactiveService
     */
    @Autowired
    public ReactivesController(ReactiveService reactiveService){
        this.reactiveService = reactiveService;
    }

    /**
     * Metoda HTTP GET pentru a obtine toate elementele Reactive din baza de date.
     *
     * @param model - Model pentru a adauga datele Reactive la vizualizarea HTML
     * @return "reactives/index" - pagina HTML pentru afisarea tuturor elementelor Reactive
     */
    @GetMapping()
    public String getAll(Model model){
        return listByPage(model, 1,"name", "asc", 5);
    }

    /**
     * Metoda HTTP GET pentru a obtine elementele Reactive din baza de date pe pagina curenta.
     *
     * @param model - Model pentru a adauga datele Reactive la vizualizarea HTML
     * @param currentPage - numarul paginii curente
     * @param sortField - campul de sortare (ex. name)
     * @param sortDir - directia de sortare (asc/desc)
     * @param size - numarul de elemente pe pagina
     * @return "reactives/index" - pagina HTML pentru afisarea elementelor Reactive
     */
    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model,
                             @PathVariable("pageNumber") int currentPage,
                             @PathParam("sortField") String sortField,
                             @PathParam("sortDir") String sortDir,
                             @PathParam("size") Integer size){

        Page<Reactive> page = reactiveService.findAllPagination(currentPage, sortField, sortDir, size);
        List<Reactive> reactiveList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("reactiveList", reactiveList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        model.addAttribute("size", size);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "reactives/index";
    }

    /**
     * Metoda HTTP GET pentru a obtine un element Reactive din baza de date in functie de id.
     *
     * @param id - id-ul elementului Reactive
     * @param model - Model pentru a adauga datele Reactive la vizualizarea HTML
     * @return "reactives/show" - pagina HTML pentru afisarea detaliilor unui element Reactive
     */
    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model){
        model.addAttribute("reactive", reactiveService.findOne(id));
        return "reactives/show";
    }

    /**
     * Metoda de afisare a paginii de adaugare a unui reactiv nou.
     *
     * @param reactive Entitatea Reactive care urmeaza sa fie adaugata.
     * @return String cu numele paginii de adaugare.
     */
    @GetMapping("/new")
    public String newReactive(@ModelAttribute("reactive") Reactive reactive){
        return "reactives/new";
    }

    /**
     * Metoda de creare a unui reactiv nou.
     *
     * @param reactive reactivul care urmeaza sa fie creata.
     * @return String cu numele paginii care urmeaza sa fie afisata dupa creare.
     */
    @PostMapping()
    public String create(@ModelAttribute("reactive") Reactive reactive){
        reactiveService.save(reactive);
        return "redirect:/reactives";
    }

    /**
     * Metoda de afisare a paginii de editare a unui reactiv existent.
     *
     * @param id Id-ul reactivului care urmeaza sa fie editata.
     * @param model Modelul care va fi folosit pentru afisarea paginii de editare.
     * @return String cu numele paginii de editare.
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("reactive", reactiveService.findOne(id));
        return "reactives/edit";
    }

    /**
     * Metoda de actualizare a unui reactiv.
     *
     * @param reactive reactivul care urmeaza sa fie actualizata.
     * @param id Id-ul reactivului care urmeaza sa fie actualizata.
     * @return String cu numele paginii care urmeaza sa fie afisata dupa actualizare.
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reactive") Reactive reactive,
                         @PathVariable("id") int id){
        reactiveService.update(id, reactive);
        return "redirect:/reactives";
    }

    /**
     * Metoda de stergere a unui reactiv existent.
     *
     * @param id Id-ul reactivului care urmeaza sa fie stearsa.
     * @return String cu numele paginii care urmeaza sa fie afisata dupa stergere.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        reactiveService.delete(id);
        return "redirect:/reactives";
    }
}
