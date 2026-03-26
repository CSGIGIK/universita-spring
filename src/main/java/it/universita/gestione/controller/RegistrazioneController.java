package it.universita.gestione.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.universita.gestione.dto.RequestStudenteDto;
import it.universita.gestione.dto.ResponseStudenteDto;
import it.universita.gestione.service.RegistrazioneStudenteService;

@RestController
@RequestMapping("/api/segreteria")
public class RegistrazioneController {
       private final RegistrazioneStudenteService registrazioneService;

        public RegistrazioneController(RegistrazioneStudenteService service) {
        this.registrazioneService = service;
        }

        @PostMapping("/registrazione-studente")
        public ResponseEntity<?> registraStudente(@RequestBody RequestStudenteDto studenteDto) {
                try {
                        ResponseStudenteDto response = registrazioneService.registrazioneStudente(studenteDto);
                        return ResponseEntity.ok(response);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());

                }

        }
}
