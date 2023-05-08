package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Order;
import com.pharma.reactives.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(Person user);
    Order findByUserAndMedicine(Person user, Medicine medicine);
}
