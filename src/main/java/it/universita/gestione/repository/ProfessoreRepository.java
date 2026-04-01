package it.universita.gestione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.universita.gestione.entity.Professore;


@Repository
public interface ProfessoreRepository extends JpaRepository<Professore, Long> {

}
