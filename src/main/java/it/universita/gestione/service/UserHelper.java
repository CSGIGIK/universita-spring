package it.universita.gestione.service;
import org.springframework.stereotype.Service;

import it.universita.gestione.repository.UtenteRepository;


@Service
public class UserHelper {
    private final UtenteRepository utenteRepository;
    
    public UserHelper(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
public boolean isUsernameTaken(String username) {
        return utenteRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return utenteRepository.existsByEmail(email);
    }
    
}
