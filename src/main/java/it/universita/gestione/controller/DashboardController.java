package it.universita.gestione.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller per il dashboard della segreteria.
 * Accessibile solo agli utenti con ruolo SEGRETERIA.
 */
@Controller
public class DashboardController {
    
    @GetMapping("/dashboard/segreteria")
    public String dashboardSegreteria() {
        return "dashboard"; // Thymeleaf cerca templates/dashboard-segreteria.html
    }
}

