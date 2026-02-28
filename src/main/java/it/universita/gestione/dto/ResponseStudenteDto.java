package it.universita.gestione.dto;

public record ResponseStudenteDto(
    String matricola,
    String codiceFiscale,
    String email,
    String username,
    String nome,
    String cognome,
    java.time.LocalDate dataNascita,
    String luogoNascita,
    String telefono,
    String nomeCorso
) {}