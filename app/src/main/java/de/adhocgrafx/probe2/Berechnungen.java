package de.adhocgrafx.probe2;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import static de.adhocgrafx.probe2.R.id.textErgebnisNoten;
import static de.adhocgrafx.probe2.R.id.textErgebnisPunkte;

/**
 * die Klasse Berechnungen repräsentiert die für das Schnitte-Programm
 * notwendigen Berechnungen des Notenschnitts.
 */

public class Berechnungen
{
    // Variablen zum Rechnen
    /** enthält die Anzahl der Arbeiten */
    static int anzahl;
    /** enthält die Anzahl der Punkte-Werte */
    static int[][] punkte = new int[16][2];
    /** enthält die Anzahl der Noten-Werte */
    static int[][] noten = new int[7][2];
    /** enthält den Punkteschnitt */
    static double punkteschnitt;
    /** enthält den Notenschnitt */
    static double notenschnitt;
    /** enthält die Prozentzahl der Noten unter N4 bzw. Punkte unter P4 */
    static double ungenuegend;

    // temporäre variablen
    static int tempnotensumme;
    static int temppunktesumme;
    static int tempungenuegend;

    // Methoden / Berechnungen
    /**
     * Methode: Punkteschnitt berechnen
     * param punktesumme
     * param anzahl
     * return: punkteschnitt
     */
    public static double punkteSchnittBerechnen(double punktesumme, int anzahl) {
        // Durchschnitt berechnen
        punkteschnitt = punktesumme / anzahl;

        // Durchschnitt ausgeben
        return (punkteschnitt);
    }

    /**
     * Methode: Notenschnitt berechnen
     * param: double notensumme, int anzahl
     * return: notenschnitt
     */
    public static double notenSchnittBerechnen(double notensumme, int anzahl) {
        // Durchschnitt berechnen
        notenschnitt = notensumme / anzahl;

        // Durchschnitt ausgeben
        return (notenschnitt);
    }

    /**
     * Methode: ungenuegend berechnen = % der Punkte/Noten unter P4/N4 berechnen
     * param: double anzahlungenuegend, int anzahl
     * return: ungenuegend
     */
    public static double ungenuegendBerechnen(double anzahlungenuegend, int anzahl) {
        // Prozentsatz berechnen
        ungenuegend = anzahlungenuegend / anzahl * 100;

        // Durchschnitt ausgeben
        return (ungenuegend);

    }

    /**
     * Methode: punkte-array mit Initialwerten füllen
     */
    public static void punkteInstanziieren() {
        for (int i = 15; i >= 0; i--) {
            // ersten zähler mit punkte-wert füllen, mit 0 - 15
            // zweiten wert mit punkte-anzahl = 0 füllen
            punkte[i][0] = i;
            punkte[i][1] = 0;
        }
    }

    /**
     * Methode: noten-array mit initialwerten füllen
     */
    public static void notenInstanziieren() {
        for (int i = 0; i < 6; i++) {
            // ersten zähler mit noten-wert füllen, mit 1 - 6
            // zweiten wert mit noten-anzahl = 0 füllen
            noten[i][0] = i + 1;
            noten[i][1] = 0;
        }
    }

    /**
     * Methode: Klausuren-Anzahl berechnen
     * return: anzahl
     */
    public static int klausurenAnzahlBerechnen() {
        // erst reset
        anzahl = 0;

        // Anzahl der Klausuren berechnen
        for (int n = 0; n <= 15; n++) {
            anzahl += punkte[n][1];
        }

        // Anzahl ausgeben
        return (anzahl);
    }

    /**
     * Methode: Schulaufgaben-Anzahl berechnen
     * return: anzahl
     */
    public static int schulaufgabenAnzahlBerechnen() {
        // erst reset
        anzahl = 0;

        // Anzahl der Klausuren berechnen
        for (int n = 1; n <= 6; n++) {
            anzahl += noten[n][1];
        }

        // Anzahl ausgeben
        return (anzahl);
    }

    /**
     * Methode: punkte-anzahlen in noten-anzahlen einfügen
     */
    public static void notenSynchronisieren() {
        // punkte-anzahlen in noten-anzahlen einfügen
        // 1 entspricht 15, 14, 13
        for (int n = 15; n > 12; n--) {
            noten[1][1] += punkte[n][1];
        }
        // 2 entspricht 12, 11, 10
        for (int n = 12; n > 9; n--) {
            noten[2][1] += punkte[n][1];
        }
        // 3 entspricht 9, 8, 7
        for (int n = 9; n > 6; n--) {
            noten[3][1] += punkte[n][1];
        }
        // 4 entspricht 6, 5, 4
        for (int n = 6; n > 3; n--) {
            noten[4][1] += punkte[n][1];
        }
        // 5 entspricht 3, 2, 1
        for (int n = 3; n > 0; n--) {
            noten[5][1] += punkte[n][1];
        }
        // 6 entspricht 0
        noten[6][1] = punkte[0][1];
    }

    /**
     * Methode temporäre Berechnungen für Punkte
     */
    public static void tempBerechnungenPunkte ()
    {
        // erst reset
        tempnotensumme = 0; temppunktesumme = 0; tempungenuegend = 0;

        for (int n = 1; n <= 6; n++)
        {
            tempnotensumme += (Berechnungen.noten[n][1] * Berechnungen.noten[n-1][0]);
        }

        for (int n = 0; n <= 15; n++)
        {
            temppunktesumme += (Berechnungen.punkte[n][1] * Berechnungen.punkte[n][0]);
        }

        for (int n = 0; n < 4; n++)
        {
            tempungenuegend += Berechnungen.punkte[n][1];
        }
    }

    /**
     * Methode temporäre Berechnungen für Noten
     */
    public static void tempBerechnungenNoten ()
    {
        // erst reset
        tempnotensumme = 0; tempungenuegend = 0;

        for (int n = 1; n <= 6; n++)
        {
            tempnotensumme += (Berechnungen.noten[n][1] * Berechnungen.noten[n-1][0]);
        }

        for (int n = 5; n <= 6; n++)
        {
            tempungenuegend += Berechnungen.noten[n][1];
        }
    }
}
