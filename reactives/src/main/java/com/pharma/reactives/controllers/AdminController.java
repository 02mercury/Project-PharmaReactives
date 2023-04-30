package com.pharma.reactives.controllers;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AdminController {

    private final AccountService accountService;

    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("users", accountService.findAll());
        return "accounts/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id,
                          Model model){
        model.addAttribute("user", accountService.findOne(id));
        return "accounts/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("user", accountService.findOne(id));
        return "accounts/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Person person,
                         @PathVariable("id") int id){
        accountService.update(id, person);
        return "redirect:/accounts";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        accountService.delete(id);
        return "redirect:/accounts";
    }
}
