//specifichiamo il package in cui si trova la classe Studentw
 package it.universita.gestione.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;








@Entity // indichiamo che questa classe è un'entità JPA
@Table(name = "studente")// indichiamo il nome della tabella nel database a cui questa classe è associata se non ce la crea automaticamente
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 

public class Studente {
    // definiamo i campi della classe Studente, che corrispondono alle colonne della
    // tabella nel database da costruire avendo db vuoto

    @Id // indichiamo che questo campo rappresenta la chiave primaria della tabella
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento del campo id, il valore sarà generato
                                                        // automaticamente dal database
    private Long id; // definiamo un campo id di tipo Long che rappresenta l'identificatore univoco
                     // dello studente

    @Column(length = 6, unique = true)
    // indichiamo che questo campo rappresenta la colonna "matricola" nella tabella
    // del database, con una lunghezza massima di 6 caratteri e un vincolo di
    // unicità

    // ====DATI IDENTIFICATIVI====//
    private String matricola; // definiamo un campo matricola di tipo String che rappresenta la matricola
                              // dello studente
    @Column(length = 16, unique = true, nullable = false)
    private String codiceFiscale;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 50, unique = true)
    private String username;

    // ====DATI ANAGRAFICI====//
    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String cognome;

    @Column()
    private java.time.LocalDate dataNascita;

    @Column(length = 100)
    private String luogoNascita;

    // ====CONTTATTI====//
    @Column(length = 20)
    private String telefono;

    // ====CREDENZIALI====//
    @Column(length = 255)
    private String password;

    // ====STATO E TIMESTAMP====//
    
    // timestamp LocaldDateTime per indicare la data e l'ora di iscrizione dello
    // studente non serve specificare
    @Column() 
    private LocalDateTime dataIscrizione;

    @Column(length = 20)
    private String ruolo= "STUDENTE"; // Valore di default 

    @Column()
    private boolean iscritto= false; // Valore di default
    
    // ====GESTIONE CONCORRENZA ====//
    @Column()
    @jakarta.persistence.Version // indichiamo che questo campo rappresenta la versione dell'entità, utilizzato
    private int versione = 0; // per la gestione della concorrenza

    // ====RELAZIONI====//
    // indichiamo che questa è una relazione ManyToOne, ovvero che molti studenti
    // possono essere iscritti a un solo corso di laurea
    @ManyToOne(fetch = FetchType.LAZY)
    // definiamo una relazione ManyToOne con la classe CorsoLaurea, indicando che
    // ogni studente è iscritto a un solo corso di laurea, ma un corso di
    // laurea può avere più studenti iscritti
    @JoinColumn(name = "id_corso_laurea")
    private CorsoLaurea corsoLaurea; 


}