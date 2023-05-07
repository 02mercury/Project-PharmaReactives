package com.pharma.reactives.repositories;

import com.pharma.reactives.models.CartItem;
import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUser(Person user);
    CartItem findByUserAndMedicine(Person user, Medicine medicine);
}
