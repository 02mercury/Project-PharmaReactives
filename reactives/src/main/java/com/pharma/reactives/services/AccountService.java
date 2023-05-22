package com.pharma.reactives.services;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.PeopleRepository;
import com.pharma.reactives.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Aceasta clasa contine servicii pentru gestionarea conturilor utilizatorilor.
 * Toate metodele din aceasta clasa sunt marcate cu anotarea @Transactional pentru a asigura
 * tranzactii atomice.
 *
 * @author Bodiu Dumitru
 */
@Service
@Transactional(readOnly = true)
public class AccountService {
    final private PeopleRepository peopleRepository;

    /**
     * Constructorul acestei clase care injecteaza dependenta catre PeopleRepository.
     * @param peopleRepository obiectul PeopleRepository care este injectat in aceasta clasa.
     */
    @Autowired
    public AccountService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * Returneaza o lista cu toate persoanele din baza de date sortate crescator dupa id.
     * @return lista cu toate persoanele din baza de date sortate crescator dupa id.
     */
    public List<Person> findAll(){
        return peopleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Cauta o persoana in baza de date cu un anumit id.
     * @param id id-ul persoanei cautate.
     * @return persoana gasita in baza de date sau null daca nu exista.
     */
    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }

    /**
     * Actualizeaza o persoana in baza de date cu un anumit id.
     * @param id id-ul persoanei de actualizat.
     * @param updatedPerson obiectul Person cu noile informatii.
     */
    @Transactional
    public void update(int id, Person updatedPerson){
        peopleRepository.findById(id).ifPresent(person -> {
            person.setRole(updatedPerson.getRole());
            person.setAccountNonLocked(updatedPerson.isAccountNonLocked());

            peopleRepository.save(person);
        });
    }

    /**
     * Sterge o persoana din baza de date cu un anumit id.
     * @param id id-ul persoanei de sters.
     */
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Person getCurrentlyLoggedInUser(Authentication authentication) {

        if (authentication == null)
            return null;

        Person user = null;
        Object principal = authentication.getPrincipal();


        if (principal == null) {
            return null;
        }

        if (principal instanceof PersonDetails) {
            user = ((PersonDetails) principal).getPerson();
        }


        return user;
    }

    public int totalAccounts(){
        return peopleRepository.totalAccounts();
    }
}
