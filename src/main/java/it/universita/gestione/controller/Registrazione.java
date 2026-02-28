package it.universita.gestione.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Registrazione {
    @GetMapping("/registrazione")
        public String mostraFormRegistrazione() {
        return "registrazione"; // Ritorna il nome della vista per la registrazione
    }
    
}
