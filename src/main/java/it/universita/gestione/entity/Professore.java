package it.universita.gestione.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ====DATI ANAGRAFICI====//

    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String cognome;

    @Column(length = 100, unique = true)
    private String email;

    // ====DATI IDENTIFICATIVI====//
    @Column(length = 10, unique = true)
    private String matricola; // (DOC001, DOC002)

    @Column(length = 50, unique = true)
    private String username;
    // ====DATI ACCESSO====//
    @Column(length = 255)
    private String password;
    // ====DATI ACCADEMICI====//
    @Column(length = 20)
    private String ruolo;

    @Column()
    private LocalDateTime dataAssunzione;

    @Column(length = 50)
    private String ruoloAccademico; // "Ordinario", "Associato", "Ricercatore"

    // ====CONTATTI====//

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String ufficio; // (es: "Aula 205")

    // ====RELAZIONI====//
    @OneToMany(mappedBy = "professore")
    private Set<Insegnamento> insegnamenti = new HashSet<>();
}
