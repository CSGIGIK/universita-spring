//specifichiamo il package in cui si trova la classe Studente
package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // indichiamo che questa classe è un'entità JPA
@Table(name = "studente") // indichiamo il nome della tabella nel database a cui questa classe è associata
                          // se non ce la crea automaticamente

public class Studente {
    // definiamo i campi della classe Studente, che corrispondono alle colonne della
    // tabella nel database da costruire avendo db vuoto

    @Id // indichiamo che questo campo rappresenta la chiave primaria della tabella
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento del campo id, il valore sarà generato
                                                        // automaticamente dal database
    private Long id; // definiamo un campo id di tipo Long che rappresenta l'identificatore univoco
                     // dello studente
    /*
     * CREATE TABLE studente (
     * id BIGINT AUTO_INCREMENT PRIMARY KEY
     */
    @Column(length = 6, unique = true) // indichiamo che questo campo rappresenta la colonna "matricola" nella tabella
                                       // del database, con una lunghezza massima di 6 caratteri e un vincolo di
                                       // unicità
    private String matricola; // definiamo un campo matricola di tipo String che rappresenta la matricola
                              // dello studente

    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String cognome;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 255)
    private String password;

    @Column() // timestamp LocaldDateTime per indicare la data e l'ora di iscrizione dello
              // studente non serve specificare
    private LocalDateTime dataIscrizione;

    @Column(length = 20)
    private String ruolo;

    @Column()
    private boolean iscritto;

    @Column()
    private int versione;
}
