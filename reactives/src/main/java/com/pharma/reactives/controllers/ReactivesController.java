package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.pdf.ReactivePDF;
import com.pharma.reactives.services.ReactiveService;
import com.pharma.reactives.util.ReactiveValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private final ReactiveValidator reactiveValidator;

    /**
     * Constructor pentru clasa ReactivesController.
     *
     * @param reactiveService   - serviciu care ofera metodele ReactiveService
     * @param reactiveValidator
     */
    @Autowired
    public ReactivesController(ReactiveService reactiveService, ReactiveValidator reactiveValidator){
        this.reactiveService = reactiveService;
        this.reactiveValidator = reactiveValidator;
    }

    /**
     * Metoda HTTP GET pentru a obtine toate elementele Reactive din baza de date.
     *
     * @param model - Model pentru a adauga datele Reactive la vizualizarea HTML
     * @return "reactives/index" - pagina HTML pentru afisarea tuturor elementelor Reactive
     */
    @GetMapping()
    public String getAll(Model model, Authentication authentication){
        return listByPage(model, authentication, 1,"id", "desc", 5);
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
                             Authentication authentication,
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

        model.addAttribute("authentication", authentication);

        model.addAttribute("size", size);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "reactives/index";
    }

    @GetMapping("/export")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=reactives_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        List<Reactive> reactiveList = reactiveService.findAll();

        ReactivePDF exporter = new ReactivePDF(reactiveList);
        exporter.export(response);
    }

    /**
     * Metoda de afisare a paginii de adaugare a unui reactiv nou.
     *
     * @return String cu numele paginii de adaugare.
     */
    @GetMapping("/new")
    public String newReactive(Model model,
                              Authentication authentication){

        model.addAttribute("reactive", new Reactive());
        model.addAttribute("authentication", authentication);
        return "reactives/new";
    }

    /**
     * Metoda de creare a unui reactiv nou.
     *
     * @param reactive reactivul care urmeaza sa fie creata.
     * @return String cu numele paginii care urmeaza sa fie afisata dupa creare.
     */
    @PostMapping()
    public String create(@ModelAttribute("reactive") @Valid Reactive reactive,
                         BindingResult bindingResult,
                         Authentication authentication,
                         Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("authentication", authentication);
            return "/reactives/new";
        }

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
                       Model model,
                       Authentication authentication){
        model.addAttribute("authentication", authentication);
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
                         @PathVariable("id") int id,
                         BindingResult bindingResult,
                         Authentication authentication,
                         Model model){
        reactiveValidator.validate(reactive, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("authentication", authentication);
            return "/reactives/edit";
        }
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
