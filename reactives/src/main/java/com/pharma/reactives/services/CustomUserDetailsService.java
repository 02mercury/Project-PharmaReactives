package com.pharma.reactives.services;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.PeopleRepository;
import com.pharma.reactives.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Clasa care implementeaza interfata UserDetailsService, fiind responsabila pentru incarcarea
 * detaliilor utilizatorului din baza de date pentru autentificare si autorizare.
 * Aceasta clasa se bazeaza pe repository-ul PeopleRepository pentru a gasi persoana asociata cu
 * numele de utilizator furnizat si pentru a genera un obiect UserDetails care poate fi utilizat
 * pentru autentificarea si autorizarea utilizatorului.
 *
 * @author Bodiu Dumitru
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * Repository-ul folosit pentru a gasi persoana asociata cu numele de utilizator.
     */
    public final PeopleRepository peopleRepository;

    /**
     * Metoda care incarca detaliile utilizatorului din baza de date pentru autentificare si autorizare.
     *
     * @param username numele de utilizator pentru care trebuie incarcate detaliile
     * @return obiectul UserDetails asociat cu utilizatorul gasit in baza de date
     * @throws UsernameNotFoundException daca numele de utilizator nu exista in baza de date
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.
                findByUsername(username);
        if(person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }
}
