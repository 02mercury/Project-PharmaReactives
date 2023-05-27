package com.pharma.reactives.services;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.repositories.ReactivesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Aceasta clasa reprezinta un serviciu care gestioneaza operatiile legate de Reactivi in cadrul aplicatiei.
 * Serviciul este marcat ca fiind readonly si utilizeaza ReactivesRepository pentru a face operatiile necesare.
 *
 * @author Bodiu Dumitru
 */
@Service
@Transactional(readOnly = true)
public class ReactiveService {
    private final ReactivesRepository reactivesRepository;

    /**
     * Constructorul clasei ReactiveService, care injecteaza o instanta a ReactivesRepository.
     * @param reactivesRepository o instanta a ReactivesRepository
     */
    @Autowired
    public ReactiveService(ReactivesRepository reactivesRepository) {
        this.reactivesRepository = reactivesRepository;
    }

    /**
     * Returneaza o lista cu toti Reactivii din baza de date.
     * @return o lista cu toti Reactivii din baza de date
     */
    public List<Reactive> findAll(){
        return reactivesRepository.findAll();
    }


    /**
     * Returneaza o pagina cu Reactivii din baza de date, sortata dupa un camp dat si directie de sortare.
     * @param pageNumber numarul paginii
     * @param sortField campul dupa care se face sortarea
     * @param sortDir directia de sortare (ascendent sau descendent)
     * @param size numarul de elemente pe pagina
     * @return o pagina cu Reactivii din baza de date
     */
    public Page<Reactive> findAllPagination(int pageNumber, String sortField, String sortDir, Integer size){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, size, sort);
        return reactivesRepository.findAll(pageable);
    }

    /**
     * Returneaza un Reactiv din baza de date, identificat prin id.
     * @param id id-ul Reactivului
     * @return Reactivul gasit, sau null daca nu a fost gasit
     */
    public Reactive findOne(int id){
        Optional<Reactive> foundReactive = reactivesRepository.findById(id);

        return foundReactive.orElse(null);
    }

    /**
     * Salveaza un Reactiv in baza de date.
     * @param reactive Reactivul care trebuie salvat
     */
    @Transactional
    public void save(Reactive reactive){
        reactivesRepository.save(reactive);
    }

    /**
     * Actualizeaza un Reactiv existent in baza de date, identificat prin id.
     * @param id id-ul Reactivului de actualizat
     * @param updatedReactive Reactivul cu noile informatii
     */
    @Transactional
    public void update(int id, Reactive updatedReactive){
        reactivesRepository.findById(id).ifPresent(reactive -> {
            reactive.setStock(updatedReactive.getStock());
            reactive.setPrice(updatedReactive.getPrice());
            reactivesRepository.save(reactive);
        });
    }

    /**
     * Sterge un Reactiv din baza de date, identificat prin id.
     * @param id id-ul Reactivului care trebuie sters
     */
    @Transactional
    public void delete(int id){
        reactivesRepository.deleteById(id);
    }

    public int total(){
        return reactivesRepository.total();
    }
}
