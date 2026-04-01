package it.universita.gestione.entity;
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

@Column(nullable=false)
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
