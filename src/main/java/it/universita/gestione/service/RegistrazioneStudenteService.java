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
                studenteSalvato.getCorsoLaurea().getNome()
                 //====FINE DTO DI RISPOSTA====//
        );
        // Ritorna il DTO di risposta al controller
        return responseDto;
       //====FINE SERVIZIO====//
    }
}
