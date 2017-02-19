package de.adhocgrafx.probe2;

/**
 * die Klasse Schnitte repräsentiert die für das Schnitte-Programm
 * notwendigen Attribute des Notenschnitts.
 */

public class Schnitte {
    // Instanzvariablen
    /** enthält Anzahl der Arbeiten  */
    int anzahl;
    /** enthält den Punkteschnitt */
    double schnittPunkte;
    /** enthält den Notenschnitt */
    double schnittNoten;
    /** enthält den % der Punkte/Noten unter P4 bzw N4 */
    double prozentUngenuegend;

    /** Konstruktoren für Objekte der Klasse Schnitte */
    public Schnitte() {
        // empty constructor
    }

    public Schnitte(int anzahl, double schnittNoten, double prozentUngenuegend)
    {
        // Instanzvariable initialisieren
        this.anzahl = anzahl;
        this.schnittNoten = schnittNoten;
        this.prozentUngenuegend = prozentUngenuegend;
    }

    public Schnitte(int anzahl, double schnittPunkte, double schnittNoten, double prozentUngenuegend)
    {
        // Instanzvariable initialisieren
        this.anzahl = anzahl;
        this.schnittPunkte = schnittPunkte;
        this.schnittNoten = schnittNoten;
        this.prozentUngenuegend = prozentUngenuegend;
    }
}
