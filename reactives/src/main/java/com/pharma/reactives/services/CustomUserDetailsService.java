package com.pharma.reactives.services;

import com.pharma.reactives.models.Person;
import com.pharma.reactives.repositories.PeopleRepository;
import com.pharma.reactives.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Person person = peopleRepository.
                findByUsername(username);
        if(person == null)
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person);
    }
}
