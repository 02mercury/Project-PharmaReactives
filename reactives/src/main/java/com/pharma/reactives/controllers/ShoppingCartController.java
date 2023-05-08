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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AccountService userService;
    private final OrderService orderService;
    private final OrderValidator orderValidator;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, AccountService userService, OrderService orderService, OrderValidator orderValidator) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderValidator = orderValidator;
    }

    @GetMapping()
    public String showShoppingCart(Model model,
                                   Authentication authentication) {
        Person user = userService.getCurrentlyLoggedInUser(authentication);
        List<CartItem> cartItems = shoppingCartService.listCartItems(user);


        double totalPrice = 0;

        for (CartItem item:
             cartItems) {
            totalPrice += item.getSubTotal();
        }

        model.addAttribute("authentication", authentication);
        model.addAttribute("totalPrice", totalPrice);

        // verificam daca utilizatorul are itemi in cos
        if(cartItems.isEmpty()) {
            model.addAttribute("message", "Nu exista itemi in cos!");
        } else {
            model.addAttribute("cartItems", cartItems);
        }

        return "cart/shopping_cart";
    }

    @PostMapping("/add/{id}")
    public String addProductToCart(@PathVariable("id") Integer medicineId,
                                   @ModelAttribute("cartItem") CartItem cartItem,
                                   Authentication authentication){
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return null;
        }

        int quantity = cartItem.getQuantity();

        Person user = userService.getCurrentlyLoggedInUser(authentication);
        shoppingCartService.addProduct(medicineId, quantity, user);

        return "redirect:/cart";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id,
                         Authentication authentication){

        Person user = userService.getCurrentlyLoggedInUser(authentication);
        shoppingCartService.deleteProduct(id, user);

        return "redirect:/cart";
    }
}
