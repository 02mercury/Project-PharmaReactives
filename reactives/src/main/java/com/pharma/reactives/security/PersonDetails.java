package com.pharma.reactives.security;

import com.pharma.reactives.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

/**
 * Clasa PersonDetails este o implementare a interfetei UserDetails, care este utilizată pentru a descrie un utilizator
 * și drepturile acestuia în cadrul aplicației. Aceasta este o clasă de suport pentru clasa Person și este utilizată
 * pentru a crea obiecte UserDetails din obiecte Person.
 *
 * @author Bodiu Dumitru
 */
public class PersonDetails implements UserDetails {

    private final Person person;

    /**
     * Constructorul clasei PersonDetails.
     * @param person obiectul Person asociat detaliilor utilizatorului
     */
    public PersonDetails(Person person) {
        this.person = person;
    }

    /**
     * Returnează autoritățile utilizatorului sub formă de colecție de obiecte GrantedAuthority.
     * @return o colecție de obiecte GrantedAuthority care reprezintă autoritățile utilizatorului
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole().toString()));
    }

    /**
     * Returnează parola utilizatorului.
     * @return parola utilizatorului
     */
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    /**
     * Returnează numele de utilizator.
     * @return numele de utilizator
     */
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    /**
     * Verifică dacă contul utilizatorului nu a expirat.
     * @return true dacă contul nu a expirat, false în caz contrar
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Verifică dacă contul utilizatorului nu este blocat.
     * @return true dacă contul nu este blocat, false în caz contrar
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.person.isAccountNonLocked();
    }

    /**
     * Verifică dacă credențialele utilizatorului nu au expirat.
     * @return true dacă credențialele nu au expirat, false în caz contrar
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Verifică dacă utilizatorul este activ.
     * @return true dacă utilizatorul este activ, false în caz contrar
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returnează obiectul Person asociat detaliilor utilizatorului.
     * @return obiectul Person asociat detaliilor utilizatorului
     */
    public Person getPerson() {
        return this.person;
    }
}
