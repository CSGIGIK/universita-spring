package it.universita.gestione.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.universita.gestione.repository.CorsoLaureaRepository;

@Controller
public class Registrazione {

    private final CorsoLaureaRepository corsoLaurea;
    public Registrazione(CorsoLaureaRepository corsoLaurea) {
        this.corsoLaurea = corsoLaurea;
    }


    @GetMapping("/registrazione")
    public String mostraFormRegistrazione(Model model) {  
    model.addAttribute("corsi", corsoLaurea.findAll());  
    return "registrazione";

    
}
}