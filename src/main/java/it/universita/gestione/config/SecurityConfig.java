package it.universita.gestione.config;
// Configurazione di sicurezza per l'applicazione
// In questa classe possiamo definire le regole di sicurezza,

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// useremo dei bean che non sono altro che componenti gestiti da Spring
// visibili in tutta l'applicazione, nel nostro caso useremo un bean per l'hashing della password come primo passo

@Configuration // Indica a Spring che questa classe contiene definizioni di bean e configurazioni
@EnableWebSecurity // Abilita la sicurezza web di Spring Security

public class SecurityConfig {
    @Bean //Crea questo oggetto e lo rende disponibile per l'iniezione in altre parti dell'applicazione
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

    // Possiamo aggiungere altre configurazioni di sicurezza qui, come la configurazione delle autorizzazioni,
    // Creiamo utente test sara un operatore della segreteria con username "test" e password "test123" (che sarà encodata)
 

        //====operatore segreteria in memoria per testare la sicurezza====
    /*    @Bean 
    public UserDetailsService operatoreTest(PasswordEncoder passwordEncoder){ // UserDetailsService è un'interfaccia che fornisce un metodo per caricare i dettagli dell'utente in base al nome utente
      UserDetails  operatore = User.builder().username("gigi")
                .password(passwordEncoder.encode("test123"))
                .roles("SEGRETERIA") // Assegna il ruolo "SEGRETERIA" all'utente che spring trasformerà in "ROLE_SEGRETERIA"
                .build(); // Costruisce un oggetto UserDetails con le informazioni dell'utente
        return new InMemoryUserDetailsManager(operatore);// Crea un UserDetailsService in memoria che contiene l'utente definito sopra
    }
    */
        //=====test concluso=====


    // ============================================
    // CONFIGURAZIONE DI SICUREZZA PRINCIPALE
    // ============================================
    // Questa configurazione definisce QUALI endpoint sono pubblici
    // QUALI richiedono login, e COME funziona l'autenticazione
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        // 1. AUTORIZZAZIONE - Chi può accedere a cosa?
        http.authorizeHttpRequests(autorizzazione -> autorizzazione
            // ✅ /login e /css/** sono PUBBLICI (non richiedono login)
            .requestMatchers("/login", "/css/**", "/", "/registrazione").permitAll()
            
            // 🔐 /dashboard/segreteria richiede il ruolo SEGRETERIA
            .requestMatchers("/dashboard/segreteria/**").hasRole("SEGRETERIA")
            
            // 🔐 /api/segreteria/** richiede il ruolo SEGRETERIA
            .requestMatchers("/api/segreteria/**").hasRole("SEGRETERIA")
            
            // 🔐 Tutto il resto richiede autenticazione
            .anyRequest().authenticated()
        );
        
        // 2. FORM LOGIN - Come funziona il login?
        // Nota: Nel nostro caso usiamo FORM LOGIN (non HTTP Basic Auth)
        http.formLogin(form -> form
            // Pagina di login: GET /login mostra il form
            .loginPage("/login")
            
            // Dove il form invia i dati: POST /login
            // Spring Security gestisce AUTOMATICAMENTE questo endpoint!
            // Non dobbiamo creare un controller per POST /login
            .loginProcessingUrl("/login")
            
            // Dove reindirizzare dopo login RIUSCITO
            // Se utente accede direttamente a /login, dopo il login lo manda qui
            .defaultSuccessUrl("/dashboard/segreteria", true)
            
            // Username e password field names
            .usernameParameter("username")
            .passwordParameter("password")
        );
        
        // 3. LOGOUT - Come gestire la disconnessione?
        http.logout(logout -> logout
            // URL per fare logout: GET /logout o POST /logout
            .logoutUrl("/logout")
            
            // Dove reindirizzare dopo logout
            .logoutSuccessUrl("/login?logout")
            
            // Invalida la sessione
            .invalidateHttpSession(true)
        );
        
        // 4. CSRF PROTECTION - Protezione da attacchi Cross-Site Request Forgery
        // IMPORTANTE: Ora è ABILITATO (prima era disabilitato!)
        // Thymeleaf aggiungerà automaticamente il token CSRF nei form
        http.csrf(Customizer.withDefaults());
        
        // 5. ACCESS DENIED
        http.exceptionHandling(exception -> exception
            // Se utente non autorizzato: GET /login
            .accessDeniedPage("/login")
        );
        
        return http.build();
    }

    
}