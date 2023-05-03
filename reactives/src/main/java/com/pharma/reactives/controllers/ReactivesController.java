package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.ReactiveService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reactives")
public class ReactivesController {
    private final ReactiveService reactiveService;

    @Autowired
    public ReactivesController(ReactiveService reactiveService){
        this.reactiveService = reactiveService;
    }

//    @GetMapping()
//    public String getAll(Model model,
//                         @RequestParam(name = "size", defaultValue = "5") Integer size,
//                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable){
//
//        Page<Reactive> page = reactiveService.findAllPagination(pageable);
//
//        model.addAttribute("data", page);
//        model.addAttribute("sizes", new Integer[]{5, 10, 20});
//        model.addAttribute("selectedSize", size);
//
//        return "reactives/index";
//    }

    @GetMapping()
    public String getAll(Model model){
        return listByPage(model, 1, "name", "asc");
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model,
                             @PathVariable("pageNumber") int currentPage,
                             @PathParam("sortField") String sortField,
                             @PathParam("sortDir") String sortDir){

        Page<Reactive> page = reactiveService.findAllPagination(currentPage, sortField, sortDir);
        List<Reactive> reactiveList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("reactiveList", reactiveList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "reactives/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model){
        model.addAttribute("reactive", reactiveService.findOne(id));
        return "reactives/show";
    }

    @GetMapping("/new")
    public String newReactive(@ModelAttribute("reactive") Reactive reactive){
        return "reactives/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("reactive") Reactive reactive){
        reactiveService.save(reactive);
        return "redirect:/reactives";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("reactive", reactiveService.findOne(id));
        return "reactives/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("reactive") Reactive reactive,
                         @PathVariable("id") int id){
        reactiveService.update(id, reactive);
        return "redirect:/reactives";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        reactiveService.delete(id);
        return "redirect:/reactives";
    }
}
