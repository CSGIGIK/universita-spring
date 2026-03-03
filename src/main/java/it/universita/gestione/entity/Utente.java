package it.universita.gestione.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utente")
@Inheritance(strategy = InheritanceType.JOINED) // specifica la strategia di ereditarietà per le classi che estendono Utente:Sudente-Professore-OperatoreSegreteria
// In questo caso, ogni classe figlia avrà la propria tabella nel database, con una relazione di chiave esterna alla tabella "utente" 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter 
//Inseriamo tutti i dati comuni a tutte le classi che estendono Utente, in modo da evitare duplicazioni di codice e semplificare la gestione degli utenti nel sistema 
//per login unificato e gestione centralizzata degli utentie in base al ruolo specifico (studente, professore, operatore di segreteria)
//avranno ora accesso a questi campi e potranno aggiungere campi specifici senza dover ripetere i campi comuni.
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 50)
    private String username;
    @Column(unique=true, length = 100)
    private String email;
    @Column(length = 255)
    private String password;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 100, nullable = false)
    private String cognome;
    @Column(length=20)
    private String telefono;
    @Column(length=20, nullable = false)
    private String ruolo; 
    
    @Column()
    @jakarta.persistence.Version // indichiamo che questo campo rappresenta la versione dell'entità, utilizzato
    private int versione = 0; // per la gestione della concorrenza
    

}
