package it.universita.gestione.config;
// Configurazione di sicurezza per l'applicazione
// In questa classe possiamo definire le regole di sicurezza,

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User; //test
//import org.springframework.security.core.userdetails.UserDetails; //test
//import org.springframework.security.core.userdetails.UserDetailsService; //test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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


    // Che autorizzazioni vogliamo dare agli utenti?
    // l'operatore segreteria per il momento avra accesso a tutte le risorse di ("/api/segreteria/**")
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{ // SecurityFilterChain gestisce la sicurezza delle richieste HTTP chi puo fare cosa?
        http.authorizeHttpRequests(autorizzazione -> autorizzazione // Configura le regole di autorizzazione per le richieste HTTP
            .requestMatchers("/api/segreteria/**").hasRole("SEGRETERIA") // Solo gli utenti con il ruolo "SEGRETERIA" possono accedere alle risorse che corrispondono al pattern "/api/segreteria/**"
            .anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).csrf(csrf -> csrf.disable()); 
            // Su siti web con form          
            return http.build(); 

    }

    
}