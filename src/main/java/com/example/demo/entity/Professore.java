package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "professore")
public class Professore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true)  
    private String matricola;             //(DOC001, DOC002)

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

    @Column(length = 20)
    private String ruolo;
    
    @Column()
    private LocalDateTime dataAssunzione;

    @Column(length=50)
    private String ruoloAccademico; // "Ordinario", "Associato", "Ricercatore"

     @Column(length = 20)             
    private String telefono;         
    
    @Column(length = 100)           
    private String ufficio;          //(es: "Aula 205")

    
    public Professore() {}
    
    public Professore(Long id, String matricola, String nome, String cognome, String email, String username,
            String password, String ruolo, LocalDateTime dataAssunzione, String ruoloAccademico, String telefono,
            String ufficio) {
        this.id = id;
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.dataAssunzione = dataAssunzione;
        this.ruoloAccademico = ruoloAccademico;
        this.telefono = telefono;
        this.ufficio = ufficio;
    }

    
    public Long getId() {
        return id;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public LocalDateTime getDataAssunzione() {
        return dataAssunzione;
    }

    public void setDataAssunzione(LocalDateTime dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    public String getRuoloAccademico() {
        return ruoloAccademico;
    }

    public void setRuoloAccademico(String ruoloAccademico) {
        this.ruoloAccademico = ruoloAccademico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUfficio() {
        return ufficio;
    }

    public void setUfficio(String ufficio) {
        this.ufficio = ufficio;
    }

}
