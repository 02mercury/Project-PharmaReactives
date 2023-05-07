package com.pharma.reactives.controllers;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.CartItemRepository;
import com.pharma.reactives.services.AccountService;
import com.pharma.reactives.services.CustomUserDetailsService;
import com.pharma.reactives.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AccountService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, AccountService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
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

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("authentication", authentication);
        model.addAttribute("totalPrice", totalPrice);
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
