package it.universita.gestione.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.universita.gestione.entity.Utente;


@Repository
public interface  UtenteRepository extends JpaRepository<Utente, Long> {
    // Aggiungi metodi di query personalizzati se necessario
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
