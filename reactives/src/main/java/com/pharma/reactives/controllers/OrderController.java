package com.pharma.reactives.controllers;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Order;
import com.pharma.reactives.models.Person;
import com.pharma.reactives.services.AccountService;
import com.pharma.reactives.services.OrderService;
import com.pharma.reactives.services.ShoppingCartService;
import com.pharma.reactives.util.OrderValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;
    private final ShoppingCartService cartService;
    private final OrderValidator orderValidator;

    @Autowired
    public OrderController(OrderService orderService, AccountService accountService, ShoppingCartService cartService, OrderValidator orderValidator) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.cartService = cartService;
        this.orderValidator = orderValidator;
    }

    @GetMapping()
    public String showOrders(Model model,
                             Authentication authentication){
        Person user = accountService.getCurrentlyLoggedInUser(authentication);
        List<Order> orders = orderService.orderList(user);

        model.addAttribute("orders", orders);
        model.addAttribute("authentication", authentication);

        return "orders/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("deliveryInfo") Order order,
                         Authentication authentication){
        Person user = accountService.getCurrentlyLoggedInUser(authentication);
        List<CartItem> cartItems = cartService.listCartItems(user);

        if(cartItems.isEmpty()){
            return "redirect:/orders";
        }

        return "orders/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("deliveryInfo") @Valid Order deliveryInfo,
                         BindingResult bindingResult,
                         Authentication authentication){

        orderValidator.validate(deliveryInfo, bindingResult);
        if(bindingResult.hasErrors())
            return "orders/new";

        Person user = accountService.getCurrentlyLoggedInUser(authentication);
        List<CartItem> cartItems = cartService.listCartItems(user);

        Timestamp placed_at = new Timestamp(System.currentTimeMillis());
        orderService.checkout(cartItems, deliveryInfo, user, placed_at);

        return "redirect:/orders";
    }
}
