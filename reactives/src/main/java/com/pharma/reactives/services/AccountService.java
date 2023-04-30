package com.pharma.reactives.services;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.PeopleRepository;
import org.hibernate.annotations.CollectionTypeRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {
    final private PeopleRepository peopleRepository;

    @Autowired
    public AccountService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        peopleRepository.findById(id).ifPresent(person -> {
            person.setRole(updatedPerson.getRole());
            person.setAccountNonLocked(updatedPerson.isAccountNonLocked());

            peopleRepository.save(person);
        });
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void lock(int id, Person lockedPerson){
        peopleRepository.findById(id).ifPresent(locked -> {
            locked.setAccountNonLocked(locked.isAccountNonLocked());

            peopleRepository.save(locked);
        });
    }

}
