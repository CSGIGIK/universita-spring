package com.example.demo.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    }


