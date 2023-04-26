# Sinn dieses Projekts

Diese Anwendung enthält ein Backend und ein Frontend. Dort kann ein Nutzer Nachrichten und optional Bilder erstellen und abrufen. Die Bilder werden vom Backend automatisch in Graustufen umgewandelt.

Im folgenden erhältst du eine Liste von Bugs und Feature-Wünschen, die du in der zur Verfügung stehenden Zeit bearbeiten sollst.

Die Aufgaben sind grob nach Schwierigkeitsgrad aufsteigend geordnet. Wenn du bei einer Aufgabe nicht weiterkommst, dann kannst du sie ohne Probleme überspringen und mit einem anderen Problem weiterarbeiten.

Am Ende sollte ein branch, auf dem der letzte Stand gepusht ist, sowie eine merge-request erstellt werden.

# Aufsetzen des Projekts

Klone zunächst dieses Repository auf deinen Computer

## Aufsetzen des Backends

Installiere Java (mindestens Version 20).

Um das Backend zu starten, wechsle in das Verzeichnis “backend”, und führe dann, abhängig von deinem Betriebssystem einen der folgenden Befehle aus:

Unter Windows:
gradlew.bat bootRun

Unter Linux/Mac:
./gradlew bootRun

Unter http://localhost:8080/docs solltest du jetzt die OpenAPI Dokumentation sehen, auf der du die Funktionalität des Backends auch direkt testen kannst.

## Aufsetzen des Frontends

Installiere die letzte stabile Version von NodeJS.

Wechsle in das Verzeichnis “frontend”.

Führe dort folgenden Befehl aus:
npm ci
(dies musst du nur einmal tun)

Um das Frontend zu starten, führe folgenden Befehl aus:
npm run dev

Unter http://localhost:5173/ (ggf. unter einem anderen Port, dies wird beim Start angezeigt) solltest du jetzt das Frontend sehen können.

# Aufgaben Frontend

1. Der User soll eine Fehlermeldung beim Eintragen der Posts erhalten, falls kein Bild ausgewählt wurde. (1 Punkt)

2. Der Kunde berichtet, dass das Text-wrapping im BlogPost nicht wirklich gut aussieht und dies soll nun behoben werden, sodass der gesamte Text in passender Größe innerhalb des BlogPost ersichtlich ist (2 Punkte)

3. Im BlogPost wird nicht der aktuelle Titel angezeigt, dies soll behoben werden (1 Punkt)

4. Die BlogPosts werden aktuell immer 4 spaltig angezeigt, der Kunde wünscht sich die Möglichkeit dies responsiv zu gestalten, sodass bei minimum 1 Spalte und maximal n Spalten angezeigt werden in relation zur Fensterbreite (3 Punkte)

5. Die aktuelle Schriftart soll global auf “Roboto” gesetzt werden (1 Punkt)

6. Vor dem Löschen eines Beitrags soll eine extra Bestätigung vom User angefordert werden (mithilfe eines Modals) (4 Punkte)

7. Der Kunde möchte, dass die Sidebar und Navigation-Bar immer fixiert sein sollen, sodass nur der Content mit den BlogPost vertikal scrollbar ist (fixed location) (5 Punkte)

8. Der User soll die Möglichkeit erhalten, nur Beiträge zu sehen, die mit einem Like markiert wurden. (6 Punkte)

9.  Der User soll die Möglichkeit erhalten die Sortierung der BlogPosts zu ändern ((auf/-ab)steigend, title, content) (5 Punkte)

10. Das Erstelldatum des Beitrags anzeigen (4 Punkte)

11. Löschen von BlogPosts funktioniert nicht (die Änderungen sind nicht persistent) (7 Punkte)

12. Die Bilder in jedem BlogPost als Carousel anzeigen (9 Punkte)

13. Es soll möglich sein, mehrere Bilder zum selben Beitrag hochzuladen (7 Punkte)

14. Der Titel des Beitrags soll editierbar sein. (7 Punkte)

15. TypeScript im Projekt mit Vue soll korrekt aufgesetzt sein. (10 Punkte)

# Aufgaben Backend

1. Nutzer berichten, dass die ausgegrauten Bilder invertiert aussehen (was weiß sein sollte ist schwarz, was schwarz sein sollte ist weiß. Finde und behebe die Ursache hierfür.

2. User berichten, dass das Hochladen von großen Bildern nicht funktioniert. Finde heraus, was die Ursache hierfür ist, und sorge dafür, dass Bilder mit bis zu 5 Megabyte verarbeitet werden können.

3. Die Funktionalität für das Ausgrauen von Bildern verwendet bisher die gleichen Gewichtungen für rot, grün und blau. Nutzer wünschen ein natürlicheres Aussehen der ausgegrauten Bilder, die besser der menschlichen Wahrnehmung von Farben entspricht.<br/>
Verwende die folgenden Werte für die Gewichtungen:
   - Rot: 0.2989
   - Grün: 0.5870
   - Blau: 0.1140

4. Nutzer wünschen sich, die Größe der Bilder angezeigt zu bekommen.<br/>
Führe eine neue Property für die Bilder namens ‘sizeInMegabytes’ ein, und sorge dafür dass sie als Festkommazahl in der Datenbank gespeichert wird.<br/>
Beachte, dass die Datenbank nach dieser Änderung neu erstellt werden muss, und dass der Daten-Typ bei der Erstellung korrekt gehandhabt werden muss.<br/>
Die Größe soll automatisch beim Erstellen eines Bildes ermittelt werden.<br/>
Die Größe soll im Frontend angezeigt werden.

5. Nutzer wünschen sich, erstellte Nachrichten wieder löschen zu können.<br/>
Wenn eine Nachricht gelöscht wird, sollen alle dazugehörigen Bilder ebenfalls gelöscht werden.<br/>
Erstelle einen entsprechenden Endpunkt am Backend, und eine entsprechendes Element im Frontend (zum Beispiel ein Button), der den Endpunkt anspricht.

6. User würden gerne Docker für das Deployment des Backends verwenden.<br/>
Im Ordner ‘backend’ existiert bereits eine Dockerfile, die aber bisher nicht vollständig implementiert ist.<br/>
Vervollständige die Dockerfile, sodass das Backend gebaut und ausgeführt werden kann.




