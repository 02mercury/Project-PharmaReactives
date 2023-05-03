package com.pharma.reactives.services;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.models.Role;
import com.pharma.reactives.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clasa care se ocupa cu serviciile de inregistrare a unui nou utilizator.
 * Aceasta clasa contine metode pentru inregistrarea unui utilizator si criptarea parolei acestuia.
 *
 * @author Bodiu Dumitru
 */
@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructorul clasei care initializeaza repository-ul si encoder-ul.
     * @param peopleRepository Repository-ul pentru stocarea informatiei despre utilizatori.
     * @param passwordEncoder Encoder-ul folosit pentru a cripta parolele utilizatorilor.
     */
    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Metoda care inregistreaza un nou utilizator in baza de date.
     * Metoda cripteaza parola utilizatorului inainte de a o salva in baza de date si seteaza rolul acestuia ca fiind USER.
     * @param person Obiectul Person care contine informatiile utilizatorului care urmeaza sa fie inregistrat.
     */
    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(Role.ROLE_USER);
        peopleRepository.save(person);
    }


}
