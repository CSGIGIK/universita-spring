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
### Studente
**File:** [src/main/java/it/universita/gestione/entity/Studente.java](src/main/java/it/universita/gestione/entity/Studente.java)

**Campi:**
- `id` (Long) - Chiave Primaria, Auto-increment
- `matricola` (String, 6 car.) - Identificatore unico dello studente
- `codiceFiscale` (String, 16 car.) - Identificatore fiscale unico
- `email` (String) - Email unica
- `username` (String, 50 car.) - Username unico per login
- `nome`, `cognome` - Dati anagrafici
- `dataNascita` (LocalDate) - Data di nascita
- `luogoNascita` (String) - Luogo di nascita
- `telefono` (String) - Numero di telefono
- `password` (String, 255 car.) - Password (deve essere encodata)
- `dataIscrizione` (LocalDateTime) - Data di iscrizione automatica
- `iscritto` (Boolean) - Stato iscrizione
- `versione` (Integer) - Versionamento
- `ruolo` (String) - Ruolo utente (default: "STUDENTE")
- **Relazione:** `corsoLaurea` (ManyToOne) - Corso di laurea associato

**Annotazioni Lombok:**
```java
@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor
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

**Responsabilità:**
1. Riceve `RequestStudenteDto` dal controller
2. Crea una nuova entità `Studente`
3. Popola i campi da DTO
4. Recupera il `CorsoLaurea` associato dal repository
5. Imposta dati automatici (data iscrizione, versione, ruolo)
6. **TODO:** Encoda la password con BCrypt
7. Salva nel database tramite `StudenteRepository`
8. Restituisce `ResponseStudenteDto`

**Metodi:**
```java
public ResponseStudenteDto registrazioneStudente(RequestStudenteDto dto)
```

---

## Controller & API

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
    
    // Lombok (Versione: 1.18.30 - Compatibile con Java 17)
    compileOnly 'org.projectlombok:lombok:1.18.30'
    annotationProcessor 'org.projectlombok:lombok:1.18.30'
    testCompileOnly 'org.projectlombok:lombok:1.18.30'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.30'
}
```

**Versioni Critiche:**
- **Java:** 17 (LTS)
- **Spring Boot:** 3.5.11
- **Lombok:** 1.18.30 (aggiornato da 1.18.36 per compatibilit� con javac)
- **MySQL Connector:** ultima versione stabile
- **Jakarta Persistence:** 3.x (inclusa in Spring Boot 3.x)

---

## Progresso Implementazione

### ? Completato
1. **Setup Progetto**
   - [x] Creazione progetto Spring Boot
   - [x] Configurazione Gradle con dipendenze
   - [x] Configurazione MySQL database `UniversitaOfficial`
   - [x] Setup JPA/Hibernate

2. **Entit� (6 classi)**
   - [x] `Studente` - Completa con relazioni
   - [x] `Professore` - Completa con relazioni
   - [x] `CorsoLaurea` - Completa con relazioni
   - [x] `Insegnamento` - Completa con relazioni
   - [x] `Dipartimento` - Completa con relazioni
   - [x] `OperatoreSegreteria` - Base implementata

3. **Layer di Accesso ai Dati**
   - [x] `StudenteRepository` - CRUD completo
   - [x] `CorsoLaureaRepository` - CRUD completo

4. **Logica di Business**
   - [x] `RegistrazioneStudenteService` - Registrazione studenti

5. **API REST**
   - [x] `RegistrazioneStudenteController` - Endpoint POST registrazione
   - [x] Mapping DTO Request ? Entity ? DTO Response

6. **DTO (Data Transfer Objects)**
   - [x] `RequestStudenteDto` - Input validation
   - [x] `ResponseStudenteDto` - Output response

7. **Risoluzione Problemi**
   - [x] Fix Lombok/Javac incompatibility 
   - [x] Verifica JPA relationships
   - [x] Build Gradle successful

### ?? In Sviluppo
1. **Security**
   - [ ] Implementare BCrypt per password encoding
   - [ ] Aggiungere Spring Security
   - [ ] JWT Token Authentication

2. **Validazione**
   - [ ] Aggiungere @Valid e Bean Validation
   - [ ] Custom validators
   - [ ] Error handling globale

3. **API Aggiuntive**
   - [ ] GET /api/segreteria/studenti - Lista studenti
   - [ ] GET /api/segreteria/studenti/{id} - Dettagli studente
   - [ ] PUT /api/segreteria/studenti/{id} - Modifica studente
   - [ ] DELETE /api/segreteria/studenti/{id} - Cancella studente

4. **Logica di Business**
   - [ ] Validazione unicit� email/username
   - [ ] Gestione iscrizione a corsi
   - [ ] Calcolo voti e media

---

## Prossimi Passaggi

### 1. Implementare Password Encoding
```java
// Nel Service: RegistrazioneStudenteService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
studente.setPassword(encoder.encode(dto.password()));
```

### 2. Aggiungere Spring Security
```gradle
implementation 'org.springframework.boot:spring-boot-starter-security'
```

### 3. Implementare Validazione Input
```java
import jakarta.validation.constraints.*;

public record RequestStudenteDto(
    @NotBlank String matricola,
    @NotBlank @Size(min=16, max=16) String codiceFiscale,
    @Email String email,
    // ... altri campi
)
```

### 4. Gestione Errori Globale
Aggiungere `@RestControllerAdvice` per centralizzare error handling.

### 5. Ampliare API
Implementare endpoint GET, PUT, DELETE per gestione completa delle risorse.

### 6. Aggiungere Logging
Integrare SLF4J + Logback per log strutturato.

---

## Risoluzione Problemi

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
- **Lazy Loading:** Configurato su relazioni OneToMany per performance
- **Transaction Management:** Gestito automaticamente da Spring
- **Versionamento:** Git-ready

---

**Data Ultimo Aggiornamento:** 28 Febbraio 2026  
**Versione Progetto:** 0.0.1-SNAPSHOT  
**Stato:** ?? In Sviluppo Attivo
