# SnakeFX & SnakeServer
Dies ist das glorreiche Repository des Moduls Patterns und Frameworks im WiSe 2020.
Dieses Repository beinhaltet die Planungsdokumente, UML-Diagramme und den Quelltext des Projekts.
Note: Die UML-Diagramme wurden mit der Software "Dia" erstellt.

## Ordner-Struktur
- docs - Enthält die Dokumentation zur Einrichtung und Nutzung des Projekts
- Prototype - Enthält prototypische Dokumentationen zu ausgewählten Aspekten
- UML - Enthält die UML-Diagramme zu verschiedenen Aspekten des Projekts
- workspace 
  - Enthält den Quelltext des Clients- als auch des Java-Backends
  - Enthält eine gemeinsame Projektbeschreibung (siehe /workspace/readme.md)

## Changelog
- 09.10.2020 - Sichtung der Anforderung, erste Besprechung der Aufgaben
- 09.10.2020 - Erster Entwurf der Diagramme, Update der Ressourcen
- 18.10.2020 - Überarbeitung der Diagramme
- 23.10.2020 - Entwurf von UI-Mockups, Ergänzung der Diagramme
- 24.10.2020 - Ergänzung der Diagramme
- 25.10.2020 - Partielle Implementierung der Schnittstelle
- 27.10.2020 - Vollständige Implementierung des Front-Ends (UI, ohne Logik - Szenenwechsel ist möglich)
- 28.10.2020 - Partielle Verbindung des Front- mit dem Back-Ends
- xy.11.2020 
  - Implementierung Snake
  - Implementierung Snake, lokaler Multiplayer
  - Implementierung Snake, lokale KI (NPC)
- 28.11.2020 - Demonstration des aktuellen Standes, Vorschau des Prototypen
- 29.11.2020 - Überarbeitung und Einarbeitung des Feedbacks, sowohl von Gruppe als auch Dozent
- 03.12.2020 - Refactoring des Projekts, Impl. d. Relationen, Refactoring des Datenmodels,
- 17.12.2020 - Refactoring des Projekts, Impl. d. Relationen, Refactoring des Datenmodels,
- 23.12.2020 
  - Refactoring des Projekts, Anbindung der UI an Backend, Abschluss d. Spielhistorie-Screens
  - Integration des STOMP-Servers & STOMP-Clients, Abschluss d. Home-Screens zu 75%
  - Impl. des Chats mittels STOMP-Service





## API / Misc.
- http://localhost:8080/h2 (user: sa | pass: <none>)
- http://localhost:8080/api/spieler/
- http://localhost:8080/api/spieler/name
- http://localhost:8080/api/lobby
- http://localhost:8080/api/historie

## Tasks
Die folgende Liste enthält Aufgaben (Tasks) welche im Zuge des Moduls realisiert wurden oder noch realisiert werden müssen.

### Frontend
- Neue UI "LoginScreen"
  - Refactoring des RegisterScreens in den LoginScreen [DONE]
  - Impl. des User-Interfaces [DONE]
  - Anfrage an den Server -> Login [DONE]
  - Registrierung mittels Frontend am Backend [DONE]
  - Login mittels Frontend am Backend [DONE]
- Neue UI "Homescreen"
  - Impl. des User-Interfaces [DONE]
  - Wechsel von Login auf Homescreen (Scene-Handling) [DONE] 
  - Neue UI "Lobby" - ist Teil vom Homescreen [DONE]
  - Button: Betrete existierendes Spiel 
- Neue UI "Erstelle Spiel" 
  - Impl. des User-Interfaces [DONE]
  - Definition einer Spielrunde [DONE]
  - Senden der SpielDefinition an das Backend  [DONE]
- Neue UI "Spielhistorie" [DONE]
  - Impl. des User-Interfaces [DONE]
  - Bezug bisheriger Spielerunden vom Backend [DONE]
  - Anbindung von Front- and Backend und vice versa [DONE]
  - Impl. der ListCells [DONE]
- Neue UI "Spielrunde" o. "Aktives Spiel"
  - Beinhaltet alle Spieler einer Spielrunde
  - Stellt die Spieler, deren Figuren, das Spielfeld und den Punktestand dar 
    - Die Spielerfiguren müssen bewegt werden
    - PowerUps dargestellt werden
    - Die Map, evtl. Hindernisse oder sonstiges
    - Punktestand jedes Spielers
- Spieler kann Ende eines anderen Spielers abbeißen
- Erzeugung eines Spiels anhand der Spieldefinition
- Teilnahme an Spiel in Lobby
- ...

### Backend
- Akzeptanz von Registrierungs-Anforderungen (API)  [DONE]
  - Erstelle einen neuen Benutzer / Spieler im Backend (DB)  [DONE]
- Akzeptanz von Login-Anforderungen (API, Session-Handling)  [DONE]
  - Abgleich mit Usern in der Datenbank, erstelle eine Session falls b vorhanden  [DONE]
- Absicherung des Logins (z.B. mit bCrypt & Salts)
- Senden der Lobbydaten für Lobby auf dem Client  [DONE]
  - Verwaltung der Lobby  [DONE]
  - Empfang neuer Spielrunden-Definition vom Backend  [DONE]
- Bezug der Spielhistorie-Daten (Spielrunden) vom Backend zum Client (Auf Anfrage) (API) [DONE]
- Spieler tritt einem existierenden Spiel in der Lobby bei (Joining)
- Spieler verlässt Spiel********
- ...

### Beantwortete Fragen
- wird eine Spielrunde client- oder serverseitig berechnet?
 - alles wird Clientseitig berechnet, einzig die Eingabedaten (Richtungsvektoren) werden an das Backend übertragen
- wie werden die Benutzereingaben während des Spiels verarbeitet?
  - Ein Spieler betätigt W,A,S oder D -> Was passiert?
  - Alternative Eingabemöglichkeiten
  
### Offene Fragen
- was passiert, wenn ein Spieler die Lobby verlässt?
 - Der Spieler wird aus der Lobby entfernt 
- was passiert, wenn ein Spieler ein aktives Spiel verlässt, oder evtl. die Verbindung unterbrochen wird?
 - Der Spieler wird als NOOP markiert, bleibt im Spiel zwar enthalten bewegt sich aber nicht mehr und kann auch nicht mehr gewinnen
 
