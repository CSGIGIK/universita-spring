package it.universita.gestione.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.universita.gestione.entity.Utente;
import it.universita.gestione.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> utenteOptional = utenteRepository.findByUsername(username);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            return User.builder()
                    .username(utente.getUsername())
                    .password(utente.getPassword())
                    .roles(utente.getRuolo())
                    .build();
        } else {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }
    }
}