package it.universita.gestione.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "professore")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professore extends Utente {
    // ====DATI IDENTIFICATIVI====//
    @Column(length = 10, unique = true)
    private String matricola;

    // ====DATI ACCADEMICI====//
    @Column()
    private LocalDateTime dataAssunzione;

    @Column(length = 50)
    private String ruoloAccademico;

    // ====CONTATTI====//
    @Column(length = 100)
    private String ufficio;

    // ====RELAZIONI====//
    @OneToMany(mappedBy = "professore")
    private Set<Insegnamento> insegnamenti = new HashSet<>();

    // ============================================================================
    // CODICE RIMPIAZZATO - TESTIMONIANZA DI STUDIO
    // I seguenti campi erano mappati direttamente in Professore ma sono stati
    // spostati nella classe base Utente per evitare duplicazione tra Studente,
    // Professore e OperatoreSegreteria -> Pattern: JOINED Inheritance
    // ============================================================================

    /*
     * CHIAVE PRIMARIA - Rimossa (ereditata da Utente):
     * 
     * @Id
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     * private Long id;
     */

    /*
     * DATI ANAGRAFICI - Spostati in Utente:
     * 
     * @Column(length = 100)
     * private String nome;
     * 
     * @Column(length = 100)
     * private String cognome;
     * 
     * @Column(length = 100, unique = true)
     * private String email;
     */

    /*
     * DATI ACCESSO - Spostati in Utente:
     * 
     * @Column(length = 50, unique = true)
     * private String username;
     * 
     * @Column(length = 255)
     * private String password;
     * 
     * @Column(length = 20)
     * private String ruolo = "PROFESSORE";
     */

    /*
     * CONTATTI - Spostati in Utente:
     * 
     * @Column(length = 20)
     * private String telefono;
     */
}
