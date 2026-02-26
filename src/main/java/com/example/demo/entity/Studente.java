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

    @Column(length = 6, unique = true)
    // indichiamo che questo campo rappresenta la colonna "matricola" nella tabella
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
    @jakarta.persistence.Version // indichiamo che questo campo rappresenta la versione dell'entità, utilizzato
    private int versione;   // per la gestione della concorrenza ottimistica

    @Column(length = 16, unique = true, nullable = false)
    private String codiceFiscale;
    
    @Column()
    private java.time.LocalDate dataNascita;

    @Column(length = 100)
    private String luogoNascita;

    @Column(length = 20)
    private String telefono;
// definiamo i costruttori, i getter e i setter per i campi della classe Studente

 public Studente() {}

    public Studente(String matricola, String nome, String cognome, String email, String username, String password,
            LocalDateTime dataIscrizione, String ruolo, boolean iscritto, int versione, String codiceFiscale,
            java.time.LocalDate dataNascita, String luogoNascita, String telefono) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dataIscrizione = dataIscrizione;
        this.ruolo = ruolo;
        this.iscritto = iscritto;
        this.versione = versione;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.telefono = telefono;
    }

//==========GETTER==========//SETTER==========//
  // ID
public Long getId() {
    return id;
}

// Matricola  
public String getMatricola() {
    return matricola;
}
public void setMatricola(String matricola) {
    this.matricola = matricola;
}

// Nome
public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}

// Cognome
public String getCognome() {
    return cognome;
}
public void setCognome(String cognome) {
    this.cognome = cognome;
}

// Email
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}

// Username
public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}

// Password
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}

// DataIscrizione
public LocalDateTime getDataIscrizione() {
    return dataIscrizione;
}
public void setDataIscrizione(LocalDateTime dataIscrizione) {
    this.dataIscrizione = dataIscrizione;
}

// Ruolo
public String getRuolo() {
    return ruolo;
}
public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
}

// Iscritto
public boolean isIscritto() {
    return iscritto;
}
public void setIscritto(boolean iscritto) {
    this.iscritto = iscritto;
}

// Versione
public int getVersione() {
    return versione;
}
public void setVersione(int versione) {
    this.versione = versione;
}

// CodiceFiscale 
public String getCodiceFiscale() {
    return codiceFiscale;
}
public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
}

// DataNascita 
public java.time.LocalDate getDataNascita() {
    return dataNascita;
}
public void setDataNascita(java.time.LocalDate dataNascita) {
    this.dataNascita = dataNascita;
}

// LuogoNascita  
public String getLuogoNascita() {
    return luogoNascita;
}
public void setLuogoNascita(String luogoNascita) {
    this.luogoNascita = luogoNascita;
}

// Telefono 
public String getTelefono() {
    return telefono;
}
public void setTelefono(String telefono) {
    this.telefono = telefono;
}
}
