package it.universita.gestione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.universita.gestione.entity.CorsoLaurea;
@Repository

public interface CorsoLaureaRepository extends JpaRepository<CorsoLaurea, Long> {
}
