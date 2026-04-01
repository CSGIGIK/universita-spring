//specifichiamo il package in cui si trova la classe Studentw
package it.universita.gestione.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // indichiamo che questa classe è un'entità JPA
@Table(name = "studente") // indichiamo il nome della tabella nel database a cui questa classe è associata
                          // se non ce la crea automaticamente
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Studente extends Utente {
    // ====DATI IDENTIFICATIVI====//
    @Column(length = 6, unique = true, nullable = false)
    private String matricola;

    @Column(length = 16, unique = true, nullable = false)
    private String codiceFiscale;

    // ====DATI ANAGRAFICI====//
    @Column()
    private java.time.LocalDate dataNascita;

    @Column(length = 100)
    private String luogoNascita;

    // ====STATO E TIMESTAMP====//
    @Column()
    private LocalDateTime dataIscrizione;

    @Column()
    private boolean iscritto = false;

    // ====RELAZIONI====//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_corso_laurea")
    private CorsoLaurea corsoLaurea;

    // ============================================================================
    // CODICE RIMPIAZZATO - TESTIMONIANZA DI STUDIO
    // I seguenti campi erano mappati direttamente in Studente ma sono stati
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
     */

    /*
     * CONTATTI - Spostati in Utente:
     * 
     * @Column(length = 20)
     * private String telefono;
     */

    /*
     * RUOLO - Spostato in Utente (con valore di default "STUDENTE"):
     * 
     * @Column(length = 20)
     * private String ruolo = "STUDENTE";
     */

    /*
     * GESTIONE CONCORRENZA - Spostata in Utente:
     * 
     * @Column()
     * @jakarta.persistence.Version
     * private int versione = 0;
     */

}