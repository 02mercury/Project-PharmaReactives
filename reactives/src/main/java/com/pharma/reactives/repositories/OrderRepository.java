package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Medicine;
import com.pharma.reactives.models.Order;
import com.pharma.reactives.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(Person user);

    @Query("SELECT COUNT(*) FROM Order o")
    int totalOrders();

    @Query("SELECT SUM(o.price) - SUM(o.quantity * m.dose * r.price) AS profit " +
            "FROM Order o " +
            "JOIN Medicine m ON o.medicine.id = m.id " +
            "JOIN Reactive r ON m.reactive.id = r.id")
    double profit();

    @Query("SELECT SUM(o.quantity * m.dose * r.price) AS costs " +
            "FROM Order o " +
            "JOIN Medicine m ON o.medicine.id = m.id " +
            "JOIN Reactive r ON m.reactive.id = r.id")
    double costs();
}
