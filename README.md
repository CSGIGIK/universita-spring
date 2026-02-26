Progetto Universita - Spring Boot 

## Indice
- [Struttura](#struttura-progetto-attuale)
- [SetupGuide](#Setup-Guide)
- [Comandi](#comandi-eseguiti)
- [Log](#Log-Avvio)
- [Problema](#Problema-Attuale)
- [Database](#Database-MySQL)
- [DbConfigJPA](#configurazione-database-jpa)
- [Nextstep](#Prossimi-Passaggi)

 
 
 ## Struttura Progetto Attuale
 C:\Users\gigi\Desktop\universita-spring
├── README.md  Questo file
└── universita/  Progetto Maven generato
├── pom.xml  File configurazione Maven
└── src/
└── main/
├── java/
└── resources/
 

## Setup Guide
Guida passo-passo per la creazione del progetto Spring Boot Universita.
 

 ## Comandi Eseguiti
1. DOWNLOAD PROGETTO Spring Initializr
powershell
# Da C:\Users\gigi\Desktop\universita-spring>
# DOWNLOAD + ESTRAZIONE + PULIZIA (1 comando)

curl -L "https://start.spring.io/starter.zip?dependencies=web,data-jpa,mysql,thymeleaf" -o temp.zip && Expand-Archive temp.zip -DestinationPath . && del temp.zip

Spring Web,Spring Data JPA,MySQL Driver,Thymeleaf

## Database MySQL
- **Schema:** `UniversitaOfficial`
- **Status:** Creato e vuoto (0 tabelle)
- **Comandi eseguiti:**
sql
CREATE DATABASE UniversitaOfficial;
USE UniversitaOfficial;
SHOW TABLES;
Risultato: 0 row(s) returned

## Configurazione Database JPA
Vedi [application.properties](src/main/resources/application.properties)
- MySQL: UniversitaOfficial (localhost:3306)
- JPA/Hibernate: ddl-auto=update (sviluppo)
- Dialect: MySQLDialect (compatibile 8.0+)

## Prossimi Passaggi 

Inizializzazione Git repository


