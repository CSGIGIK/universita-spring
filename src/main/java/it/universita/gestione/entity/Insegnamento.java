package it.universita.gestione.entity;
import jakarta.persistence.*;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "insegnamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insegnamento {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(length = 2, nullable=false)
private int cfu;

@Column(length = 100, nullable = false)
private String nomeMateria;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_corso_laurea")
private CorsoLaurea corsoLaurea;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_professore")
private Professore professore;


}
