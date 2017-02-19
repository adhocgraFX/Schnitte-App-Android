package de.adhocgrafx.probe2;

/**
 * Created by Hock on 25.12.2016.
 */

public class Klausur {

    // klausur_liste id
    public int _id;

    // allgemeine daten
    public String klausurName;
    public String kurs;
    public String semester;
    public String datum;
    public String info;

    // Klausur oder Schulaufgabe
    // true = Klausur
    public String mode;

    // schnitte
    public int anzahl;
    public double schnittPunkte;
    public double schnittNoten;
    public double prozentUngenuegend;

    // Punkte
    public int p15;
    public int p14;
    public int p13;
    public int p12;
    public int p11;
    public int p10;
    public int p9;
    public int p8;
    public int p7;
    public int p6;
    public int p5;
    public int p4;
    public int p3;
    public int p2;
    public int p1;
    public int p0;

    // Noten
    public int n1;
    public int n2;
    public int n3;
    public int n4;
    public int n5;
    public int n6;

    /**
     * Konstruktoren
     */
    public Klausur() {
        // empty
    }

    // todo variablen angeben
    public Klausur(String klausurName, String kurs, String semester, String info, String datum) {
        this.klausurName = klausurName;
        this.kurs = kurs;
        this.semester = semester;
        this.info = info;
        // id wird erst beim Einfügen in die Datenbank erzeugt
        _id = -1;
        // mode auf Klausur setzen
        mode = "klausur";
        this.datum = datum;
    }

    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }

    public String getKlausurName() {
        return klausurName;
    }
    public void setKlausurName(String klausurName) {
        this.klausurName = klausurName;
    }

    public String getKurs() {
        return kurs;
    }
    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

    public String getDatum() {
        return datum;
    }
    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getAnzahl() {
        return anzahl;
    }
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public double getSchnittPunkte() {
        return schnittPunkte;
    }
    public void setSchnittPunkte(double schnittPunkte) {
        this.schnittPunkte = schnittPunkte;
    }

    public double getSchnittNoten() {
        return schnittNoten;
    }
    public void setSchnittNoten(double schnittNoten) {
        this.schnittNoten = schnittNoten;
    }

    public double getProzentUngenuegend() {
        return prozentUngenuegend;
    }
    public void setProzentUngenuegend(double prozentUngenuegend) {
        this.prozentUngenuegend = prozentUngenuegend;
    }

    public int getP15() {
        return p15;
    }
    public void setP15(int p15) {
        this.p15 = p15;
    }

    public int getP14() {
        return p14;
    }
    public void setP14(int p14) {
        this.p14 = p14;
    }

    public int getP13() {
        return p13;
    }
    public void setP13(int p13) {
        this.p13 = p13;
    }

    public int getP12() {
        return p12;
    }
    public void setP12(int p12) {
        this.p12 = p12;
    }

    public int getP11() {
        return p11;
    }
    public void setP11(int p11) {
        this.p11 = p11;
    }

    public int getP10() {
        return p10;
    }
    public void setP10(int p10) {
        this.p10 = p10;
    }

    public int getP9() {
        return p9;
    }
    public void setP9(int p9) {
        this.p9 = p9;
    }

    public int getP8() {
        return p8;
    }
    public void setP8(int p8) {
        this.p8 = p8;
    }

    public int getP7() {
        return p7;
    }
    public void setP7(int p7) {
        this.p7 = p7;
    }

    public int getP6() {
        return p6;
    }
    public void setP6(int p6) {
        this.p6 = p6;
    }

    public int getP5() {
        return p5;
    }
    public void setP5(int p5) {
        this.p5 = p5;
    }

    public int getP4() {
        return p4;
    }
    public void setP4(int p4) {
        this.p4 = p4;
    }

    public int getP3() {
        return p3;
    }
    public void setP3(int p3) {
        this.p3 = p3;
    }

    public int getP2() {
        return p2;
    }
    public void setP2(int p2) {
        this.p2 = p2;
    }

    public int getP1() {
        return p1;
    }
    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP0() {
        return p0;
    }
    public void setP0(int p0) {
        this.p0 = p0;
    }

    public int getN1() {
        return n1;
    }
    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }
    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }
    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getN4() {
        return n4;
    }
    public void setN4(int n4) {
        this.n4 = n4;
    }

    public int getN5() {
        return n5;
    }
    public void setN5(int n5) {
        this.n5 = n5;
    }

    public int getN6() {
        return n6;
    }
    public void setN6(int n6) {
        this.n6 = n6;
    }
}
