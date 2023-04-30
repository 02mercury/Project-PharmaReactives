package com.pharma.reactives.repositories;

import com.pharma.reactives.models.Reactive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactivesRepository extends JpaRepository<Reactive, Integer> {

}
