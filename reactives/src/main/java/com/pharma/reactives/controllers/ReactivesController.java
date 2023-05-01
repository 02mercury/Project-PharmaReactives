package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.services.ReactiveService;
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
//    public String getAll(Model model){
//        model.addAttribute("reactives", reactiveService.findAll());
//        return "reactives/index";
//    }

    @GetMapping()
    public String getAll(Model model,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable){

        Page<Reactive> page = reactiveService.findAllPagination(pageable);

        model.addAttribute("data", page);
        model.addAttribute("sizes", new Integer[]{5, 10, 20});
        model.addAttribute("selectedSize", size);

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
