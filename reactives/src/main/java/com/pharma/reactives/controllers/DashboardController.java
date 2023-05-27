package com.pharma.reactives.controllers;

import com.pharma.reactives.services.AccountService;
import com.pharma.reactives.services.MedicineService;
import com.pharma.reactives.services.OrderService;
import com.pharma.reactives.services.ReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;

@Slf4j
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final MedicineService medicineService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final ReactiveService reactiveService;

    public DashboardController(MedicineService medicineService, AccountService accountService, OrderService orderService, ReactiveService reactiveService) {
        this.medicineService = medicineService;
        this.accountService = accountService;
        this.orderService = orderService;
        this.reactiveService = reactiveService;
    }

    @GetMapping()
    public String dashboard(Authentication authentication,
                            Model model){

        int totalOrders = orderService.totalOrders();

        int totalAccounts = accountService.totalAccounts();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double profit = orderService.profit();
        double costs = orderService.costs();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int totalReactives = reactiveService.total();
        int totalMedicines = medicineService.total();
        double total = profit + costs;

        double percentage = (profit / total) * 100;

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalReactives", totalReactives);
        model.addAttribute("totalMedicines", totalMedicines);
        model.addAttribute("totalAccounts", totalAccounts);
        model.addAttribute("total", Float.valueOf(decimalFormat.format(total)));
        model.addAttribute("profit", Float.valueOf(decimalFormat.format(profit)));
        model.addAttribute("costs", Float.valueOf(decimalFormat.format(costs)));
        model.addAttribute("percentage", Float.valueOf(decimalFormat.format(percentage)));
        model.addAttribute("authentication", authentication);

        return "dashboard/index";
    }
}
