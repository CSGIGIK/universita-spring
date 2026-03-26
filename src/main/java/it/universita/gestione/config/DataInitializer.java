package it.universita.gestione.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.universita.gestione.entity.OperatoreSegreteria;
import it.universita.gestione.repository.UtenteRepository;



@Component
public class DataInitializer implements CommandLineRunner {
private final UtenteRepository utenteRepository;
private final PasswordEncoder passwordEncoder;

public DataInitializer(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
    this.utenteRepository = utenteRepository;
    this.passwordEncoder = passwordEncoder;
}
    @Override
    public void run(String... args) throws Exception { // Questo metodo viene eseguito all'avvio dell'applicazione
        // 1. Controlliamo se esiste già un utente "admin"
        if (!utenteRepository.existsByUsername("admin")) {
            
            // 2. Se non esiste, creiamo il primo operatore
            OperatoreSegreteria admin = new OperatoreSegreteria();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setEmail("admin@universita.it");
            admin.setNome("Admin");
            admin.setCognome("Sistema");
            admin.setRuolo("SEGRETERIA"); // Il ruolo che abbiamo protetto nel filterChain
            
            // 3. Salviamo nel database
            utenteRepository.save(admin);
            
            System.out.println(">>>> UTENTE ADMIN CREATO: admin / admin123 <<<<");
        } else {
            System.out.println(">>>> UTENTE ADMIN GIA' PRESENTE <<<<");
        }
    }
}