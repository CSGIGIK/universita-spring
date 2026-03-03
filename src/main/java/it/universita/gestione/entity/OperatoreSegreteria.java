package it.universita.gestione.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "operatore_segreteria")
@Getter
@Setter
public class OperatoreSegreteria extends Utente {
    
}
