package it.universita.gestione.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * AuthController gestisce l'autenticazione degli operatori di segreteria.
 * 
 * FLUSSO:
 * 1. Utente accede a GET /login
 * 2. Questo controller ritorna il template login.html
 * 3. Utente compila il form (username + password)
 * 4. Form fa POST /login (gestito automaticamente da Spring Security)
 * 5. Spring Security verifica le credenziali nel database
 * 6. Se OK → reindirizza a /dashboard/segreteria
 * 7. Se Fallisce → reindirizza back a /login?error
 */
@Controller
public class AuthController {
    
    /**
     * Mostra la pagina di login.
     * 
     * IMPORTANTE:
     * - URL: GET /login
     * - Accessibile da CHIUNQUE (non richiede autenticazione)
     * - Ritorna il template login.html
     * 
     * @return "login" → Spring cerca src/main/resources/templates/login.html
     */
    @GetMapping("/login")
    public String showLoginPage() {
        // Thymeleaf cercherà il file "login.html" nella cartella templates
        return "login";
    }
}
