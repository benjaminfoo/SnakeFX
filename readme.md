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
09.10.2020 - Sichtung der Anforderung, erste Besprechung der Aufgaben
09.10.2020 - Erster Entwurf der Diagramme, Update der Ressourcen
18.10.2020 - Überarbeitung der Diagramme
23.10.2020 - Entwurf von UI-Mockups, Ergänzung der Diagramme
24.10.2020 - Ergänzung der Diagramme

## Offene Tasks
Die folgende Liste enthält Aufgaben (Tasks) welche im Zuge des Moduls realisiert werden müssen

### Frontend
- Registrierung mittels Frontend am Backend
- Login mittels Frontend am Backend
- Neue UI "Homescreen"
  - Wechsel von Login auf Homescreen (Scene-Handling)
  - Neue UI "Lobby" - ist Teil vom Homescreen
  - Button: Betrete existierendes Spiel
- Neue UI "Erstelle Spiel"
  - Definition einer Spielrunde
  - Senden der Spielrunde an das Backend
- Neue UI "Spielhistorie"
  - Bezug bisheriger Spielerunden vom Backend
- Neue UI "Spielrunde" o. "Aktives Spiel"
  - Beinhaltet alle Spieler einer Spielrunde
  - Stellt die Spieler, deren Figuren, das Spielfeld und den Punktestand dar 
    - Die Spielerfiguren müssen bewegt werden
    - PowerUps dargestellt werden
    - Die Map, evtl. Hindernisse oder sonstiges
    - Punktestand jedes Spielers
- ...

### Backend
- Akzeptanz von Registrierungs-Anforderungen (API)
  - Erstelle einen neuen Benutzer / Spieler im Backend (DB)
- Akzeptanz von Login-Anforderungen (API, Session-Handling)
  - Abgleich mit Usern in der Datenbank, erstelle eine Session falls b vorhanden
- Absicherung des Logins (z.B. mit bCrypt & Salts)
- Senden der Lobbydaten für Lobby auf dem Client
  - Verwaltung der Lobby
  - Empfang neuer Spielrunden-Definition vom Backend
- Bezug der Spielhistorie-Daten (Spielrunden) vom Backend zum Client (Auf Anfrage) (API)
- Spieler tritt einem existierenden Spiel in der Lobby bei (Joining)
- Spieler verlässt Spiel
- ...


### Offene Fragen
- wird eine Spielrunde client- oder serverseitig berechnet?
  - Position der Spieler
  - Punkte der Spieler
  - ...
- wie werden die Benutzereingaben während des Spiels verarbeitet?
  - Ein Spieler betätigt W,A,S oder D -> Was passiert?
  - Alternative Eingabemöglichkeiten
- was passiert, wenn ein Spieler die Lobby verlässt?
- was passiert, wenn ein Spieler ein aktives Spiel verlässt?
  - oder evtl. die Verbindung unterbrochen wird?
 
