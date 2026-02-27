package it.universita.gestione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.universita.gestione.entity.Studente;
// Repository per l'entità Studente, estende JpaRepository per fornire operazioni CRUD e query personalizzate insomma parla con il database
// Funzionamento : Spring all'avvio quando fa la sua esplorazione 
// Cerca nell'entity ,la tabella studente, guarda l'etichetta id che deve essere di tipo long 
// poi prepara i metodi 
@Repository
public interface StudRepository extends JpaRepository<Studente, Long> { 
    
}

// ==================================(Operazioni CRUD Base):=======================================

// 1. SALVATAGGIO / AGGIORNAMENTO
// repository.save(studente); 
// Funzionamento: Se l'oggetto non ha un ID, fa una "INSERT". 
// Se ha già un ID, fa una "UPDATE" su quella riga. [1.2]

// 2. RICERCA PER CHIAVE PRIMARIA
// Optional<Studente> s = repository.findById(1L);
// Funzionamento: Cerca nella colonna @Id. Restituisce un 'Optional' 
// per evitare errori se lo studente non esiste (evita il NullPointerException). [1.1]

// 3. RECUPERO TUTTI I DATI
// List<Studente> lista = repository.findAll();
// Funzionamento: Esegue una "SELECT * FROM studente" e trasforma 
// ogni riga del database in un oggetto Java della classe Studente. [1.2]

// 4. ELIMINAZIONE
// repository.deleteById(1L);
// Funzionamento: Cerca la riga con quell'ID e la cancella (DELETE). [1.2]


// =======================================METODI EXTRA======================================= 

// 5. CONTEGGIO
// long totale = repository.count();
// Funzionamento: Restituisce il numero totale di righe (SELECT COUNT(*)). [1.2]

// 6. VERIFICA ESISTENZA
// boolean esiste = repository.existsById(1L);
// Funzionamento: Ti dice 'true' o 'false' senza dover scaricare tutti i dati. [1.1]

// 7. SALVATAGGIO MULTIPLO (BATCH)
// repository.saveAll(listaStudenti);
// Funzionamento: Salva un'intera lista in un colpo solo (più veloce di molti .save singoli).