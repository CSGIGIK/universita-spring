package it.universita.gestione.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.universita.gestione.repository.UtenteRepository;



@Service
public class UserHelper {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserHelper(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }
                //=====CHECK TAKEN=====//    
    public boolean isUsernameTaken(String username) {
        return utenteRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return utenteRepository.existsByEmail(email);
    }
            //=====HASHING PASSWORD=====//
    public String encodePassword(String passwordRaw) {
        return passwordEncoder.encode(passwordRaw);
    }

}
