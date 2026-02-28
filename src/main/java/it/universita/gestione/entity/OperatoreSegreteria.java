package it.universita.gestione.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "operatore_segreteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatoreSegreteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255) 
    // La password sarà salvata criptata (hash), quindi serve spazio (255)
    private String password;

    @Column(length = 100)
    private String nomeOperatore;

    @Column(length = 20)
    private String ruolo = "ADMIN"; // Valore di default per distinguere i poteri
}
