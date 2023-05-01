package com.pharma.reactives.services;

import com.pharma.reactives.models.Reactive;
import com.pharma.reactives.repositories.ReactivesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReactiveService {
    private final ReactivesRepository reactivesRepository;
    @Autowired
    public ReactiveService(ReactivesRepository reactivesRepository) {
        this.reactivesRepository = reactivesRepository;
    }

    public List<Reactive> findAll(){
        return reactivesRepository.findAll();
    }

    public Page<Reactive> findAllPagination(Pageable pageable){
        return reactivesRepository.findAll(pageable);
    }

    public Reactive findOne(int id){
        Optional<Reactive> foundReactive = reactivesRepository.findById(id);

        return foundReactive.orElse(null);
    }

    @Transactional
    public void save(Reactive reactive){
        reactivesRepository.save(reactive);
    }

    @Transactional
    public void update(int id, Reactive updatedReactive){
        reactivesRepository.findById(id).ifPresent(reactive -> {
            reactive.setStock(updatedReactive.getStock());
            reactive.setPrice(updatedReactive.getPrice());
            reactivesRepository.save(reactive);
        });
    }

    @Transactional
    public void delete(int id){
        reactivesRepository.deleteById(id);
    }
}
