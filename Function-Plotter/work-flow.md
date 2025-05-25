Natürlich! Hier ist ein übersichtlicher Workflow-Chart für dein Funktionsplotter-Projekt, der alle wesentlichen Schritte von der Idee bis zum fertigen Projekt abbildet. Ich beschreibe die Schritte als nummerierte Liste und als ASCII-Flowchart. Auf Wunsch kann ich das Diagramm auch als PlantUML- oder DOT-Code liefern.

---

## **Workflow-Chart: Funktionsplotter-Projekt**

---

## Laufende Aufgaben

- [ ] **README.md aktuell halten**

---

## Chronologische Aufgaben

### **1. Projektvorbereitung**
- [X] Projektstruktur in IntelliJ anlegen
- [X] Git-Repository initialisieren
- [ ] Abhängigkeiten/Libraries einrichten (z.B. Expr4j, XChart, JavaFX, JSVG)

### **2. Eingabeverarbeitung**
- [ ] GUI für die Eingabe von mathematischen Ausdrücken (Infix/UPN)
- [ ] CSS-Stylesheet für GUI-Design
- [X] Tokenizer implementieren
- [X] Zwei Parser (Infix & UPN) entwickeln/integrieren

### **3. AST-Erstellung**
- [X] Parser erzeugt einen abstrakten Syntaxbaum (AST) mit modernen Java-Features (Records, sealed Interfaces)
- [ ] Fehlerbehandlung bei ungültigen Ausdrücken

### **4. AST-Visualisierung**
- [ ] AST als Baumdiagramm visualisieren (z.B. DOT/Graphviz oder JavaFX)
- [ ] Anzeige der Infix- und UPN-Notation

### **5. Funktionsauswertung**
- [ ] AST mit unterschiedlichen x-Werten evaluieren
- [ ] Unterstützung für Konstanten und Funktionen (sin, cos, log, etc.)

### **6. Plotten der Funktion**
- [ ] Plot der Funktion im kartesischen Koordinatensystem (SVG-Erstellung)
- [ ] Anzeige in der GUI

### **7. Erweiterungen**
- [ ] Komfortfunktionen: Zoom, Bereichsauswahl, mehrere Funktionen, Parameter
- [ ] Zusätzliche Features wie logische Ausdrücke, Achsenskalierung, etc.

### **8. Dokumentation & LVP**
- [ ] Integration in Live View Programming (LVP)

### **9. Testen & Qualitätssicherung**
- [ ] Funktionstests, Usability-Tests
- [ ] Einhaltung der Java-Kodierstandards prüfen

### **10. Abgabe**
- [ ] Projekt als ZIP und lauffähige JAR für LVP packen
- [ ] Abgabe gemäß Vorgaben
