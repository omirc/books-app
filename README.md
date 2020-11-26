# books-app

`books-app` este o aplicatie CRUD (create-read-update-delete) pentru managementul unei biblioteci de carti.

---
URL de start: http://localhost:8080

Consola H2: http://localhost:8080/h2-console

### Instructiuni pentru pornirea aplicatiei

a) `mvn clean install`
b) dupa ce contextul `Spring` este complet initializat, uitati-va dupa cursorul `shell>` (stanga jos pe terminal windows) si tastati urmatorul text pentru a incarca in baza de date o colectie de carti prestabilita ("seed"):

     shell>insert

c) deschideti browserul cu adresa `http://localhost:8080` pentru a utiliza aplicatia

### Tehnologii folosite:
- Spring BOOT
- PostgreSQL/H2
- Spring Shell: https://spring.io/projects/spring-shell
- Spring JPA
- Spring JDBC
- Lombok
- Thymeleaf
- REST services
- Java 8
- HTML/CSS (mini-default.min.css): https://github.com/Chalarangelo/mini.css/blob/master/dist/mini-default.min.css

### Lucruri de imbunatatit
- folosire layout pt continut repetitiv (header, footer)
- folosire PUT/DELETE ca verbe in themeleaf
- validare formular frontend
- adaugare mesaj generic de eroare si mesaj de succes la operatiunea de stergere

### Licenta

MIT