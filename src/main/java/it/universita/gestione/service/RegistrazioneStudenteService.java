package it.universita.gestione.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.universita.gestione.dto.RequestStudenteDto;
import it.universita.gestione.dto.ResponseStudenteDto;
import it.universita.gestione.entity.CorsoLaurea;
import it.universita.gestione.entity.Studente;
import it.universita.gestione.repository.CorsoLaureaRepository;
import it.universita.gestione.repository.StudenteRepository;

@Service
public class RegistrazioneStudenteService {
     // Iniettio i Repository
    @Autowired
    StudenteRepository studenteRepository;
    @Autowired
    CorsoLaureaRepository corsoLaureaRepository;
//ci arriverà un DTO Request e noi ritorneremo un DTO di risposta
    public ResponseStudenteDto registrazioneStudente(RequestStudenteDto dto) {


        Studente studente = new Studente();
        studente.setMatricola(dto.matricola());
        studente.setCodiceFiscale(dto.codiceFiscale());
        studente.setEmail(dto.email());
        studente.setUsername(dto.username());
        studente.setNome(dto.nome());
        studente.setCognome(dto.cognome());
        studente.setDataNascita(dto.dataNascita());
        studente.setLuogoNascita(dto.luogoNascita());
        studente.setTelefono(dto.telefono());

        // La password deve essere encodata prima di essere salvata 
        // Importa: import org.springframework.security.crypto.bcrypt.BCrypt;
        // studente.setPassword(passwordEncoder.encode(dto.password()));
        studente.setPassword(dto.password()); // TODO: Encodare la password con BCrypt o simili

        // Recupera il corso di laurea associato usando l'ID fornito nel DTO
        // Se il corso di laurea non viene trovato, lancia un'eccezione
        CorsoLaurea corso = corsoLaureaRepository.findById(dto.idCorsoLaurea())
                .orElseThrow(() -> new RuntimeException("Corso di laurea non trovato con ID: " + dto.idCorsoLaurea()));
        studente.setCorsoLaurea(corso);

        //====FINE DTO REQUESTED====//

        //====REMPIO ENTITY====//

        studente.setDataIscrizione(java.time.LocalDateTime.now());
        studente.setIscritto(true); // Imposta lo studente come iscritto
        studente.setVersione(1); // Imposta la versione iniziale a 1
        studente.setRuolo("STUDENTE"); // Imposta il ruolo iniziale a "STUDENTE"
        
        Studente studenteSalvato = studenteRepository.save(studente);
         //====FINE SALVATAGGIO====//

         //====PREPARO DTO DI RISPOSTA====//
        ResponseStudenteDto responseDto = new ResponseStudenteDto(
                studenteSalvato.getMatricola(),
                studenteSalvato.getCodiceFiscale(),
                studenteSalvato.getEmail(),
                studenteSalvato.getUsername(),
                studenteSalvato.getNome(),
                studenteSalvato.getCognome(),
                studenteSalvato.getDataNascita(),
                studenteSalvato.getLuogoNascita(),
                studenteSalvato.getTelefono(),
                //Rispondiamo solo con il nome del corso di laurea, non con l'intero oggetto 
                //perche contiene hashset di insegnamenti e studenti che porterebberoa a cicli infinito nella serializzazione JSON
                studenteSalvato.getCorsoLaurea().getNome()
                 //====FINE DTO DI RISPOSTA====//
        );
        // Ritorna il DTO di risposta al controller
        return responseDto;
       //====FINE SERVIZIO====//
    }
}


/*======TEST=======*/
/*curl -X POST http://localhost:8080/api/segreteria/registrazione-studente 
-H "Content-Type: application/json" ^
-d "{\"username\":\"gigi88\",\"email\":\"gigi.verdi@email.it\",\"password\":\"password123\",\"nome\":\"Luigi\",\"cognome\":\"Verdi\",\"telefono\":\"3331234567\",\"matricola\":\"123456\",\"codiceFiscale\":\"VRDLGU80A01H501Z\",\"dataNascita\":\"1998-05-20\",\"luogoNascita\":\"Cosenza\",\"idCorsoLaurea\":1}"
//==== ESITO TEST ====//
// L'architettura JOINED Inheritance funziona correttamente:
// 1. Hibernate esegue una SELECT di verifica sul Corso di Laurea.
// 2. Viene inserita una riga nella tabella 'utente' (credenziali e anagrafica base).
// 3. Viene inserita una riga nella tabella 'studente' (dati accademici) usando lo STESSO ID generato dal padre.
// I dati vengono smistati correttamente tra le tabelle.
//====================//
*/
