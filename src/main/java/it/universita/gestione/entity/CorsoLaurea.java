package it.universita.gestione.entity;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "corso_laurea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorsoLaurea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dipartimento")
    private Dipartimento dipartimento;
    
    @OneToMany(mappedBy = "corsoLaurea", fetch = FetchType.LAZY)
    private Set<Insegnamento> insegnamenti = new HashSet<>();

    @OneToMany(mappedBy = "corsoLaurea", fetch = FetchType.LAZY)
    private Set<Studente> studenti = new HashSet<>();
    
}