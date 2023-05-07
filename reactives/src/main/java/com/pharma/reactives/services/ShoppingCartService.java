package com.pharma.reactives.services;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.CartItemRepository;
import com.pharma.reactives.repositories.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShoppingCartService {

    private final CartItemRepository cartItemRepository;
    private final MedicineRepository medicineRepository;


    @Autowired
    public ShoppingCartService(CartItemRepository cartItemRepository, MedicineRepository medicineRepository) {
        this.cartItemRepository = cartItemRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<CartItem> listCartItems(Person user) {
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    public void addProduct(int id, int quantity, Person user){
        int addedQuantity = quantity;

        Medicine medicine = medicineRepository.findById(id).get();

        CartItem cartItem = cartItemRepository.findByUserAndMedicine(user, medicine);

        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setMedicine(medicine);
            cartItem.setUser(user);

        }

        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteProduct(int id, Person user){
        Medicine medicine = medicineRepository.findById(id).get();
        CartItem cartItem = cartItemRepository.findByUserAndMedicine(user, medicine);

        if(cartItem != null)
            cartItemRepository.deleteById(cartItem.getId());
    }


}
