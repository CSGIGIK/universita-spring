package it.universita.gestione.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.universita.gestione.entity.OperatoreSegreteria;
@Repository
public interface OperatoreSegreteriaRepository extends JpaRepository<OperatoreSegreteria, Long> {
}

