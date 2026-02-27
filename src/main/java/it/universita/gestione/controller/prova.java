package it.universita.gestione.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class prova {
    @GetMapping("/") 
    public String home() {
        return "Benvenuto nel sistema di gestione dell'Università!";
    }

}
