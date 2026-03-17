# Progetto Universita - Spring Boot 

## Indice
- [Struttura](#struttura-progetto-attuale)
- [Setup Guide](#setup-guide)
- [Comandi Eseguiti](#comandi-eseguiti)
- [Database](#database-mysql)
- [Configurazione JPA](#configurazione-database-jpa)
- [Architettura](#architettura-del-progetto)
- [Entity](#entity)
- [Repository](#repository)
- [Service Layer](#service-layer)
- [Controller & API](#controller--api)
- [DTO](#data-transfer-objects-dto)
- [Dipendenze](#dipendenze)
- [Progresso Implementazione](#progresso-implementazione)
- [Prossimi Passaggi](#prossimi-passaggi)
- [Risoluzione Problemi](#risoluzione-problemi)

## Struttura Progetto Attuale
```
C:\Users\gigi\Desktop\universita-spring
├── README.md                       # Questo file
├── build.gradle                    # Configurazione Gradle con dipendenze
├── settings.gradle                 # Impostazioni Gradle
├── gradlew / gradlew.bat           # Gradle Wrapper per build
├── src/
│   ├── main/
│   │   ├── java/it/universita/gestione/
│   │   │   ├── DemoApplication.java
│   │   │   ├── controller/         # REST Controller
│   │   │   │   ├── RegistrazioneStudenteController.java
│   │   │   │   ├── Registrazione.java
│   │   │   │   └── prova.java
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   │   ├── RequestStudenteDto.java
│   │   │   │   └── ResponseStudenteDto.java
│   │   │   ├── entity/             # Entità JPA
│   │   │   │   ├── CorsoLaurea.java
│   │   │   │   ├── Dipartimento.java
│   │   │   │   ├── Insegnamento.java
│   │   │   │   ├── OperatoreSegreteria.java
│   │   │   │   ├── Professore.java
│   │   │   │   └── Studente.java
│   │   │   ├── repository/         # Repository (Data Access)
│   │   │   │   ├── CorsoLaureaRepository.java
│   │   │   │   └── StudenteRepository.java
│   │   │   └── service/            # Logica di business
│   │   │       └── RegistrazioneStudenteService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
└── build/                          # Output della build
```

## Setup Guide
Guida per la creazione del progetto Spring Boot Universita.
 
## Comandi Eseguiti
1. **DOWNLOAD PROGETTO Spring Initializr**
```powershell
# Da C:\Users\gigi\Desktop\universita-spring>
curl -L "https://start.spring.io/starter.zip?dependencies=web,data-jpa,mysql,thymeleaf" -o temp.zip && Expand-Archive temp.zip -DestinationPath . && del temp.zip
```

Dipendenze selezionate:
- Spring Web
- Spring Data JPA
- MySQL Driver
- Thymeleaf

## Database MySQL
- **Schema:** `UniversitaOfficial`
- **Status:** ATTIVO
- **Comandi eseguiti:**

```sql
CREATE DATABASE UniversitaOfficial;
USE UniversitaOfficial;
SHOW TABLES;
``` 

Risultato: 0 row(s) returned (tabelle create automaticamente da JPA)

## Configurazione Database JPA
Vedi [application.properties](src/main/resources/application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/UniversitaOfficial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=Root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

- **MySQL:** UniversitaOfficial (localhost:3306)
- **JPA/Hibernate:** ddl-auto=update (sviluppo)
- **Dialect:** MySQL8Dialect (compatibile 8.0+)

## Architettura del Progetto
Il progetto segue l'architettura **MVC (Model-View-Controller)** con layer di servizio:

```
Client (HTTP Request)
    ↓
Controller (/api/segreteria/registrazione-studente)
    ↓
Service (Logica di business)
    ↓
Repository (Data Access Layer)
    ↓
Database (MySQL)
```

### Flusso di una Richiesta
1. **Client** invia POST request con JSON
2. **Controller** riceve la richiesta e converte il JSON in DTO
3. **Service** valida i dati e applica la logica di business
4. **Repository** salva i dati nel database tramite JPA
5. **Response** viene restituita al client con codice HTTP

## Entity

### 🔑 Utente (Super-class Base)
**File:** [src/main/java/it/universita/gestione/entity/Utente.java](src/main/java/it/universita/gestione/entity/Utente.java)

**Strategia Eredità:** `JOINED` - Ogni sottoclasse ha una tabella specifica collegata a `utente` tramite chiave esterna.

**Campi Comuni (Ereditati da tutte le sottoclassi):**
- `id` (Long) - Chiave Primaria, Auto-increment
- `username` (String, 50 car.) - Username unico per login (unique)
- `email` (String, 100 car.) - Email unica (unique)
- `password` (String, 255 car.) - Password encodata
- `nome` (String, 100 car.) - Nome (not null)
- `cognome` (String, 100 car.) - Cognome (not null)
- `telefono` (String, 20 car.) - Numero di telefono
- `ruolo` (String, 20 car.) - Ruolo dell'utente (not null)
- `versione` (Integer) - Versionamento ottimistico (for concurrency)

**Annotazioni Lombok:**
```java
@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor
```

**Tabella Database:**
```sql
CREATE TABLE utente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    ruolo VARCHAR(20) NOT NULL,
    versione INT DEFAULT 0,
    dtype VARCHAR(31)
);
```

---

### Studente (Sottoclasse di Utente)
**File:** [src/main/java/it/universita/gestione/entity/Studente.java](src/main/java/it/universita/gestione/entity/Studente.java)

**Campi Specifici:**
- `matricola` (String, 6 car.) - Identificatore unico dello studente (unique)
- `codiceFiscale` (String, 16 car.) - Codice Fiscale (unique, not null)
- `dataNascita` (LocalDate) - Data di nascita
- `luogoNascita` (String) - Luogo di nascita
- `dataIscrizione` (LocalDateTime) - Data di iscrizione automatica
- `iscritto` (Boolean) - Stato iscrizione

**Campi Ereditati da Utente:**
- `id`, `username`, `email`, `password`, `nome`, `cognome`, `telefono`, `ruolo`, `versione`

**Relazioni:**
- **`corsoLaurea` (ManyToOne)** - Corso di laurea associato

**Annotazioni Lombok:**
```java
@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor
```

**Tabella Database:**
```sql
CREATE TABLE studente (
    id BIGINT PRIMARY KEY,
    matricola VARCHAR(6) UNIQUE,
    codice_fiscale VARCHAR(16) UNIQUE NOT NULL,
    data_nascita DATE,
    luogo_nascita VARCHAR(100),
    data_iscrizione DATETIME,
    iscritto BOOLEAN,
    id_corso_laurea BIGINT,
    FOREIGN KEY (id) REFERENCES utente(id),
    FOREIGN KEY (id_corso_laurea) REFERENCES corso_laurea(id)
);
```

---

### Professore
**File:** [src/main/java/it/universita/gestione/entity/Professore.java](src/main/java/it/universita/gestione/entity/Professore.java)

**Campi:**
- `id` (Long) - Chiave Primaria, Auto-increment
- `nome`, `cognome` (String, 100 car.) - Dati anagrafici
- `email` (String) - Email unica
- `matricola` (String, 10 car.) - Matricola docente (es. DOC001)
- `username` (String, 50 car.) - Username
- `password` (String, 255 car.) - Password
- `ruolo` (String) - Ruolo (default: "PROFESSORE")
- **Relazione:** `insegnamenti` (OneToMany) - Insegnamenti tenuti

**Annotazioni Lombok:**
```java
@Data, @NoArgsConstructor, @AllArgsConstructor
```

---

### CorsoLaurea (Degree Program)
**File:** [src/main/java/it/universita/gestione/entity/CorsoLaurea.java](src/main/java/it/universita/gestione/entity/CorsoLaurea.java)

**Campi:**
- `id` (Long) - Chiave Primaria
- `nome` (String, 100 car.) - Nome del corso (es. "Ingegneria Informatica")
- **Relazioni:**
  - `dipartimento` (ManyToOne) - Dipartimento di appartenenza
  - `insegnamenti` (OneToMany) - Insegnamenti offerti
  - `studenti` (OneToMany) - Studenti iscritti

---

### Insegnamento (Course/Subject)
**File:** [src/main/java/it/universita/gestione/entity/Insegnamento.java](src/main/java/it/universita/gestione/entity/Insegnamento.java)

**Campi:**
- `id` (Long) - Chiave Primaria
- `cfu` (int) - Crediti Formativi Universitari
- `nomeMateria` (String, 100 car.) - Nome della materia
- **Relazioni:**
  - `corsoLaurea` (ManyToOne) - Corso di laurea
  - `professore` (ManyToOne) - Professore titolare

---

### Dipartimento (Department)
**File:** [src/main/java/it/universita/gestione/entity/Dipartimento.java](src/main/java/it/universita/gestione/entity/Dipartimento.java)

**Campi:**
- `id` (Long) - Chiave Primaria
- `nome` (String, 100 car.) - Nome del dipartimento
- **Relazione:** `corsiLaurea` (OneToMany) - Corsi di laurea del dipartimento

---

### OperatoreSegreteria (Secretary Operator)
**File:** [src/main/java/it/universita/gestione/entity/OperatoreSegreteria.java](src/main/java/it/universita/gestione/entity/OperatoreSegreteria.java)

**Campi:**
- `id` (Long) - Chiave Primaria
- `username` (String, 50 car.) - Username unico
- Ulteriori campi in sviluppo

---

## Repository

### StudenteRepository
**File:** [src/main/java/it/universita/gestione/repository/StudenteRepository.java](src/main/java/it/universita/gestione/repository/StudenteRepository.java)

Interfaccia che estende `JpaRepository<Studente, Long>` per operazioni CRUD:
- `save()` - Salva uno studente
- `findById()` - Ricerca per ID
- `findAll()` - Recupera tutti gli studenti
- `update()` - Modifica uno studente
- `delete()` - Cancella uno studente

### CorsoLaureaRepository
**File:** [src/main/java/it/universita/gestione/repository/CorsoLaureaRepository.java](src/main/java/it/universita/gestione/repository/CorsoLaureaRepository.java)

Interfaccia per operazioni sui corsi di laurea:
- `findById()` - Recupera un corso per ID
- Supporta query custom

---

## Service Layer

### RegistrazioneStudenteService
**File:** [src/main/java/it/universita/gestione/service/RegistrazioneStudenteService.java](src/main/java/it/universita/gestione/service/RegistrazioneStudenteService.java)

**Dipendenze Iniettate:**
- `StudenteRepository` - Per operazioni CRUD su studenti
- `CorsoLaureaRepository` - Per recuperare il corso di laurea
- `UserHelper` - Per validazioni e password encoding

**Responsabilità:**
1. Valida username unico (tramite `UserHelper`)
2. Valida email unica (tramite `UserHelper`)
3. Riceve `RequestStudenteDto` dal controller
4. Crea una nuova entità `Studente` (sottoclasse di `Utente`)
5. Popola i campi specifici da DTO
6. **Encoda la password con BCrypt** (tramite `UserHelper`)
7. Recupera il `CorsoLaurea` associato dal repository
8. Imposta dati di sistema (data iscrizione, versione, ruolo)
9. Salva nel database tramite `StudenteRepository`
10. Restituisce `ResponseStudenteDto`

**Metodo Principale:**
```java
public ResponseStudenteDto registrazioneStudente(RequestStudenteDto dto)
```

**Validazioni Implementate:**
```java
if (userHelper.isUsernameTaken(dto.username())) {
    throw new RuntimeException("Username già in uso: " + dto.username());
}
if (userHelper.isEmailTaken(dto.email())){
    throw new RuntimeException("Email già in uso: " + dto.email());
}
```

**Password Encoding:**
```java
studente.setPassword(userHelper.encodePassword(dto.password()));
```

---

### UserHelper
**File:** [src/main/java/it/universita/gestione/service/UserHelper.java](src/main/java/it/universita/gestione/service/UserHelper.java)

**Responsabilità:**
- Validazione unicità username
- Validazione unicità email
- Encoding password con BCrypt
- Verifica password (match durante login)

**Metodi Disponibili:**
```java
public boolean isUsernameTaken(String username)        // Verifica se username è già in uso
public boolean isEmailTaken(String email)              // Verifica se email è già in uso
public String encodePassword(String plainPassword)     // Encoda password con BCrypt
public boolean matchesPassword(String plainPassword, String encodedPassword)  // Verifica password
```

**Implementazione BCrypt:**
- Utilizza `BCryptPasswordEncoder` da Spring Security
- Ogni password è unique grazie al salt randomico
- Standard OWASP per password hashing

---

### CustomUserDetailsService (Spring Security Integration)
**File:** [src/main/java/it/universita/gestione/security/CustomUserDetailsService.java](src/main/java/it/universita/gestione/security/CustomUserDetailsService.java)

**Responsabilità:**
- Implementa `UserDetailsService` di Spring Security
- Carica gli utenti dal database in tempo reale
- Fornisce credenziali per l'autenticazione HTTP Basic
- Integrazione con UtenteRepository per ricerca dinamica

**Metodo Principale:**
```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
```

**Flusso:**
1. Spring Security chiama questo metodo con l'username dalla richiesta
2. Esegue query: `utenteRepository.findByUsername(username)`
3. Se trovato → restituisce UserDetails con username, password (encodata), e ruolo
4. Se non trovato → lancia `UsernameNotFoundException`
5. Spring Security verifica la password inserita vs password nel DB usando BCrypt

**Vantaggi:**
- ✅ Utenti dinamici del database (non hardcoded)
- ✅ Sincronizzazione automatica con DB
- ✅ Supporta tutti i tipi di utenti (Studente, Professore, OperatoreSegreteria)
- ✅ Password sempre encodate non a rischio esposizione

## Configurazione Sicurezza

### SecurityConfig
**File:** [src/main/java/it/universita/gestione/config/SecurityConfig.java](src/main/java/it/universita/gestione/config/SecurityConfig.java)

**Configurazioni Implementate:**
- Bean `BCryptPasswordEncoder` per password hashing
- Configurazione HTTP Security con CSRF disabilitato (per testing)
- Integrazione con `CustomUserDetailsService` per autenticazione da database
- Endpoint `/api/segreteria/**` richiedono ruolo SEGRETERIA
- Tutti gli altri endpoint richiedono autenticazione

**Codice Configurazione:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(autorizzazione -> autorizzazione
                .requestMatchers("/api/segreteria/**").hasRole("SEGRETERIA")
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
```

---

### CustomUserDetailsService
**File:** [src/main/java/it/universita/gestione/security/CustomUserDetailsService.java](src/main/java/it/universita/gestione/security/CustomUserDetailsService.java)

**Responsabilità (Autenticazione da Database):**
1. Implementa `UserDetailsService` di Spring Security
2. Ricerca l'utente nel database tramite `UtenteRepository.findByUsername()`
3. Se trovato, restituisce un oggetto `UserDetails` con:
   - username
   - password (encodata in BCrypt)
   - ruolo (STUDENTE, PROFESSORE, SEGRETERIA, etc.)
4. Se non trovato, lancia `UsernameNotFoundException`

**Flusso di Autenticazione:**
```
Client invia credenziali (username + password)
        ↓
Spring Security chiama CustomUserDetailsService.loadUserByUsername()
        ↓
Ricerca nel database tramite UtenteRepository
        ↓
Se trovato: verifica password con BCrypt
        ↓
Se match: accesso concesso
Se no match o non trovato: accesso negato (401 Unauthorized)
```

**Codice Implementazione:**
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UtenteRepository utenteRepository;
    
    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        Optional<Utente> utenteOptional = utenteRepository.findByUsername(username);
        
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            return User.builder()
                    .username(utente.getUsername())
                    .password(utente.getPassword())  // Già encodata in BCrypt
                    .roles(utente.getRuolo())
                    .build();
        } else {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }
    }
}
```

**Differenza con SecurityConfig in memoria:**
- ❌ **Before:** Utente hardcoded in memoria (solo per test)
- ✅ **Now:** Legge da database in tempo reale (utenti dinamici)

---

### RegistrazioneStudenteController
**File:** [src/main/java/it/universita/gestione/controller/RegistrazioneStudenteController.java`](c://Users//gigi//Desktop//universita-spring//src//main//java//it//universita//gestione//controller//RegistrazioneStudenteController.java)
**Endpoint:**
```
POST /api/segreteria/registrazione-studente
Content-Type: application/json
```

**Request Body (JSON):**
```json
{
  "matricola": "MAT001",
  "codiceFiscale": "RSSMRA80A01H501Z",
  "email": "student@univ.it",
  "username": "jrossi",
  "nome": "Marco",
  "cognome": "Rossi",
  "dataNascita": "2000-01-01",
  "luogoNascita": "Roma",
  "telefono": "+39 123456789",
  "password": "SecurePassword123",
  "idCorsoLaurea": 1
}
```

**Response (200 OK):**
```json
{
  "matricola": "MAT001",
  "codiceFiscale": "RSSMRA80A01H501Z",
  "email": "student@univ.it",
  "username": "jrossi",
  "nome": "Marco",
  "cognome": "Rossi",
  "dataNascita": "2000-01-01",
  "luogoNascita": "Roma",
  "telefono": "+39 123456789",
  "nomeCorso": "Ingegneria Informatica"
}
```

**Error Response (400 Bad Request):**
```json
{
  "error": "Corso di laurea non trovato con ID: 999"
}
```

---

## Data Transfer Objects (DTO)

### RequestStudenteDto
**File:** [src/main/java/it/universita/gestione/dto/RequestStudenteDto.java](src/main/java/it/universita/gestione/dto/RequestStudenteDto.java)

**Tipo:** Record (Immutable)
```java
public record RequestStudenteDto(
    String matricola,
    String codiceFiscale,
    String email,
    String username,
    String nome,
    String cognome,
    LocalDate dataNascita,
    String luogoNascita,
    String telefono,
    String password,
    Long idCorsoLaurea
)
```

**Utilizzo:** Mappa i dati dal frontend per la registrazione

### ResponseStudenteDto
**File:** [src/main/java/it/universita/gestione/dto/ResponseStudenteDto.java](src/main/java/it/universita/gestione/dto/ResponseStudenteDto.java)

**Tipo:** Record (Immutable)
```java
public record ResponseStudenteDto(
    String matricola,
    String codiceFiscale,
    String email,
    String username,
    String nome,
    String cognome,
    LocalDate dataNascita,
    String luogoNascita,
    String telefono,
    String nomeCorso
)
```

**Utilizzo:** Restituisce i dati dello studente registrato al client

---

## Dipendenze

### build.gradle
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.11'
    id 'io.spring.dependency-management' version '1.1.7'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    // Spring Framework
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    
    // Database
    runtimeOnly 'com.mysql:mysql-connector-j'
    
    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    
    // Lombok (Versione: 1.18.42 - Nota: 1.18.30 è più stabile per Java 17)
    compileOnly 'org.projectlombok:lombok:1.18.42'
    annotationProcessor 'org.projectlombok:lombok:1.18.42'
    testCompileOnly 'org.projectlombok:lombok:1.18.42'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.42'
}
```

**Versioni Critiche:**
- **Java:** 17 (LTS)
- **Spring Boot:** 3.5.11
- **Lombok:** 1.18.42 ⚠️ (considerare aggiornare a 1.18.30 per migliore compatibilità con javac)
- **Spring Security:** Integrata per BCryptPasswordEncoder
- **MySQL Connector:** Driver JDBC incluso
- **Jakarta Persistence:** 3.x (inclusa in Spring Boot 3.x)

---

## Progresso Implementazione

### ✅ Completato
1. **Setup Progetto**
   - [x] Creazione progetto Spring Boot
   - [x] Configurazione Gradle con dipendenze
   - [x] Configurazione MySQL database `UniversitaOfficial`
   - [x] Setup JPA/Hibernate

2. **Entity (7 classi)**
   - [x] `Utente` - Super-class con strategia JOINED inheritance
   - [x] `Studente` - Sottoclasse di Utente con campi specifici
   - [x] `Professore` - Sottoclasse di Utenteg
   - [x] `CorsoLaurea` - Con relazioni OneToMany
   - [x] `Insegnamento` - Con relazioni ManyToOne
   - [x] `Dipartimento` - Con relazioni OneToMany
   - [x] `OperatoreSegreteria` - Sottoclasse di Utente

3. **Layer di Accesso ai Dati**
   - [x] `StudenteRepository` - CRUD completo
   - [x] `CorsoLaureaRepository` - CRUD completo

4. **Logica di Business**
   - [x] `RegistrazioneStudenteService` - Registrazione studenti con validazioni
   - [x] `UserHelper` - Service per password encoding e validazioni

5. **API REST**
   - [x] `RegistrazioneStudenteController` - Endpoint POST registrazione
   - [x] Mapping DTO Request → Entity → DTO Response
   - [x] Error handling per risorse non trovate

6. **DTO (Data Transfer Objects)**
   - [x] `RequestStudenteDto` - Record con validazione dati
   - [x] `ResponseStudenteDto` - Record per risposta

7. **Sicurezza**
   - [x] Spring Security integrato
   - [x] BCrypt Password Encoder implementato via UserHelper
   - [x] SecurityConfig creato
   - [x] Validazione unicità username/email
   - [x] Password encoding in UserHelper
   - [x] **CustomUserDetailsService implementato** - Autenticazione da database
   - [x] **Legge utenti da database in tempo reale** (non hardcoded)

8. **Risoluzione Problemi**
   - [x] Fix Lombok/Javac incompatibility
   - [x] Verifica JPA relationships con JOINED inheritance
   - [x] Build Gradle successful

### 🔄 In Sviluppo
1. **Testing Sicurezza con Utenti nel Database**
   - [ ] Implementare endpoint per inserire utenti di test
   - [ ] Creare script SQL per inizializzare utenti di test
   - [ ] Test su Postman/Curl con HTTP Basic Auth
   - [ ] Verifica che le password siano correttamente encodate

2. **JWT Token Authentication**
   - [ ] Implementare JWT per stateless authentication
   - [ ] Aggiungere token refresh

2. **Validazione Avanzata**
   - [ ] Aggiungere @Valid e Bean Validation (Jakarta Validation)
   - [ ] Custom validators per campi specifici
   - [ ] Global exception handler con @RestControllerAdvice

3. **API Aggiuntive**
   - [ ] GET /api/segreteria/studenti - Lista studenti (con pagination)
   - [ ] GET /api/segreteria/studenti/{id} - Dettagli studente
   - [ ] PUT /api/segreteria/studenti/{id} - Modifica studente
   - [ ] DELETE /api/segreteria/studenti/{id} - Cancella studente

4. **Logica di Business Avanzata**
   - [ ] Gestione iscrizione a corsi
   - [ ] Calcolo voti e media
   - [ ] Generazione libretti

### 📊 Stato Generale
- **Code Completion:** ~70%
- **Security:** ✅ Implementata password encoding
- **Architecture:** ✅ JOINED inheritance per utenti
- **Testing:** ⏳ Necessari unit test

---

## Prossimi Passaggi

### ✅ 1. Password Encoding - COMPLETATO
```java
// Implementato in UserHelper.java
public String encodePassword(String plainPassword) {
    return passwordEncoder.encode(plainPassword);
}
```

### ✅ 2. Spring Security - COMPLETATO
```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
```
SecurityConfig.java configurato con BCryptPasswordEncoder Bean.

### 3. Implementare Validazione Input (PROSSIMO)
```java
import jakarta.validation.constraints.*;

public record RequestStudenteDto(
    @NotBlank String matricola,
    @NotBlank @Size(min=16, max=16) String codiceFiscale,
    @Email String email,
    @NotBlank String username,
    // ... altri campi
)
```

### 4. Gestione Errori Globale
Aggiungere `@RestControllerAdvice` per centralizzare error handling con:
- Exception handler per ValidationException
- Exception handler per ResourceNotFoundException
- Exception handler per generic Exception

### 5. Ampliare API REST
Implementare endpoint aggiuntivi:
```java
@GetMapping("/api/segreteria/studenti")              // GET - Lista con pagination
@GetMapping("/api/segreteria/studenti/{id}")         // GET - Dettagli
@PutMapping("/api/segreteria/studenti/{id}")         // PUT - Modifica
@DeleteMapping("/api/segreteria/studenti/{id}")      // DELETE - Cancella
```

### 6. JWT Token Authentication
- Aggiungere dipendenza `jjwt`
- Implementare `JwtTokenProvider`
- Creare endpoint `/api/auth/login`
- Aggiungere `JwtAuthenticationFilter`

### 7. Aggiungere Logging
Integrare SLF4J + Logback per log strutturato

---

## Testing Sicurezza Autenticazione

### Confronto: Prima vs Dopo

| Aspetto | Prima (In Memoria) | Dopo (Database) |
|---------|-------------------|-----------------|
| **Origine Utenti** | Hardcoded in SecurityConfig | Letti dinamicamente da database |
| **Numero Utenti** | 1 solo (username: gigi) | Illimitati (tutti in tabella `utente`) |
| **Aggiunta Utenti** | Modifica codice + rebuild | Registrazione API o INSERT SQL |
| **Password** | "test123" (visibile nel codice) | Encodate in BCrypt (non visibili) |
| **Service Utilizzato** | InMemoryUserDetailsManager (commentato) | **CustomUserDetailsService (attivo)** |
| **Scalabilità** | Non scalabile per produzione | Pronto per produzione |
| **Sincronizzazione** | Nessuna (statica) | Real-time dal database |

### Prerequisito: Inserire un Utente di Test nel Database

Poiché `CustomUserDetailsService` legge gli utenti dal database, è necessario:

1. **Inserire un utente direttamente nel DB con contraseña encodata in BCrypt:**

```sql
-- Genera una password encodata con BCrypt (es. "test123" → "$2a$10$...")
-- Puoi usare https://bcrypt-online.com/ oppure il servizio interno di Spring
INSERT INTO utente (username, email, password, nome, cognome, ruolo, versione) 
VALUES ('gigi', 'gigi@università.it', '$2a$10$...', 'Gigi', 'Rossi', 'SEGRETERIA', 0);
```

2. **Oppure registrare uno studente via API:**

```bash
curl -X POST http://localhost:8080/api/segreteria/registrazione-studente \
-H "Content-Type: application/json" \
-d '{
  "matricola": "MAT001",
  "codiceFiscale": "RSSMRA80A01H501Z",
  "email": "student@univ.it",
  "username": "jrossi",
  "nome": "Marco",
  "cognome": "Rossi",
  "dataNascita": "2000-01-01",
  "luogoNascita": "Roma",
  "telefono": "+39 123456789",
  "password": "SecurePassword123",
  "idCorsoLaurea": 1
}'
```

### Test Autenticazione HTTP Basic

```bash
# ✅ ACCESSO CONCESSO (credenziali corrette)
curl -X GET http://localhost:8080/api/segreteria/studenti \
  -H "Authorization: Basic Z2lnaToKdGVzdDEyMw==" 
  # username: gigi, password: test123

# ❌ ACCESSO NEGATO (credenziali errate)
curl -X GET http://localhost:8080/api/segreteria/studenti \
  -H "Authorization: Basic aW52YWxpZDppbnZhbGlk"
  # username: invalid, password: invalid
  # Risposta: 401 Unauthorized
```

### Como Generare Base64 per HTTP Basic Auth

```bash
# Su Linux/Mac
echo -n "gigi:test123" | base64
# Risposta: Z2lnaTp0ZXN0MTIz

# Su Windows PowerShell
[System.Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes("gigi:test123"))
# Risposta: Z2lnaTp0ZXN0MTIz

# Poi usa nel header Authorization
-H "Authorization: Basic Z2lnaTp0ZXN0MTIz"
```

### Flusso di Sicurezza Completo

```
1. Client invia: GET /api/segreteria/studenti
   + Header: Authorization: Basic Z2lnaTp0ZXN0MTIz
   
2. Spring Security intercetta la richiesta
   
3. Chiama CustomUserDetailsService.loadUserByUsername("gigi")
   
4. CustomUserDetailsService esegue: utenteRepository.findByUsername("gigi")
   
5. Database ritorna l'utente con password encodata
   
6. Spring Security verifica che il ruolo sia SEGRETERIA
   
7. BCryptPasswordEncoder verifica password inserita vs password nel DB
   
8. Se tutto OK → accesso permesso (200 OK)
   Se fallisce → 401 Unauthorized
```

---

### ? Problema: Lombok Javac Initialization Error
**Errore:**
```
java.lang.NoClassDefFoundError: Could not initialize class lombok.javac.Javac
java.lang.ExceptionInInitializerError: Exception java.lang.NoSuchFieldException: com.sun.tools.javac.code.TypeTag :: UNKNOWN
```

**Causa:** Incompatibilit� tra versione Lombok e Java/Javac

**Soluzione Applicata:**
```gradle
// ? VECCHIO
compileOnly 'org.projectlombok:lombok:1.18.36'

// ? NUOVO - Compatibile con Java 17+
compileOnly 'org.projectlombok:lombok:1.18.30'
```

**Passi di Fix:**
1. Aggiornare `build.gradle` con versione 1.18.30
2. Eseguire: `./gradlew clean build --no-daemon`
3. Ricaricare il progetto nell'IDE

---

## Note di Sviluppo

- **Package Structure:** `it.universita.gestione.*`
- **Database Dialect:** MySQL8Dialect (MySQL 8.0+)
- **JPA Persistence:** Jakarta EE (jakarta.persistence.*)
- **Inheritance Strategy:** JOINED per gerarchia utenti (Utente → Studente, Professore, OperatoreSegreteria)
- **Lazy Loading:** Configurato su relazioni OneToMany per performance
- **Transaction Management:** Gestito automaticamente da Spring
- **Password Security:** BCrypt con salt randomico (via UserHelper)
- **Autenticazione:** HTTP Basic Auth legge utenti da database via CustomUserDetailsService
- **Database Query Dinamica:** Ogni richiesta di login queryizza il database per verificare le credenziali
- **Security Pattern:** Spring Security auto-rileva CustomUserDetailsService come @Service
- **Versionamento:** Git-ready

---

## Changelog

### Versione 0.0.2-SNAPSHOT (Corrente - 17 Marzo 2026)

**Features Implementate:**
- **CustomUserDetailsService** - Autenticazione lettura utenti da database
- **Integrazione Spring Security completa** - Utilizza CustomUserDetailsService per carica dinamica utenti
- **HTTP Basic Authentication** - Supporto credenziali username/password
- **Password Hashing BCrypt** - Password encodate e verificate dinamicamente
- Password Encoding con salt randomico
- Validazione unicità username/email
- Endpoint separati per registrazione (pubblico) e risorse protette (richiedono autenticazione)

**Architettura Sicurezza:**
- ✅ Utenti letti da database in tempo reale
- ✅ Password encodate con BCrypt (non modificabili direttamente)
- ✅ Autorizzazione basata su ruoli (ROLE_SEGRETERIA, ROLE_STUDENTE, etc.)
- ✅ HTTP Basic Auth per testing

**Non Presente (test in memoria rimosso):**
- ❌ InMemoryUserDetailsManager commentato da SecurityConfig
- ❌ Utenti hardcoded - Tutti devono essere inseriti in database

---

### Versione 0.0.1-SNAPSHOT (Precedente)

**Features Implementate:**
- Entity inheritance JOINED per Utente (super-class)
- Studente come sottoclasse di Utente
- Service per password encoding BCrypt
- Validazione unicità username/email
- SecurityConfig con BCryptPasswordEncoder Bean
- Iniettò costruttore nel service
- Gestione eccezioni per risorse non trovate

**Bug Fix:**
- Risolto: Lombok Javac incompatibility (1.18.36 → 1.18.42)
- Risolto: Missing getter/setter in sub-classes (JOINED inheritance)

---

**Data Ultimo Aggiornamento:** 17 Marzo 2026  
**Versione Progetto:** 0.0.2-SNAPSHOT  
**Stato:** 🟢 In Sviluppo Attivo (Security ✅, Architecture ✅, Database Auth ✅)
