package it.universita.gestione.dto;

import java.time.LocalDate;


/**
 * DTO per la registrazione di uno Studente.
 * Mappa i dati provenienti dal frontend e include l'ID del corso di laurea.
 */

/**
 * Un Record è una classe speciale che funge da contenitore di dati immutabile.
 * 
 * 1. I parametri tra parentesi sono i "componenti" (campi private final).
 * 2. Non servono Getter/Setter perché Java genera metodi con lo stesso nome dei campi.
 * 3. non contiene logica, ma solo dati di trasporto.
 */
public record RequestStudenteDto(
    // ==== DATI IDENTIFICATIVI ====
    String matricola,
    String codiceFiscale,
    String email,
    String username,

    // ==== DATI ANAGRAFICI ====
    String nome,
    String cognome,
    LocalDate dataNascita,
    String luogoNascita,

    // ==== CONTATTI E CREDENZIALI ====
    String telefono,
    String password, // In chiaro dal form, verrà encodata nel Service

    // ==== RELAZIONI ====
    Long idCorsoLaurea // Riceviamo solo l'ID per collegare il corso esistente
) {}
   


