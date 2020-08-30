package de.adhocgrafx.probe2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Hock on 09.01.2017.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    // todo hier variablen hinzufügen
    public static final String TABLE_NAME="klausuren";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_KLAUSURNAME = "klausur_name";
    public static final String COLUMN_KURS = "kurs";
    public static final String COLUMN_SEMESTER = "semester";
    public static final String COLUMN_INFO = "info";
    public static final String COLUMN_MODE = "mode";
    public static final String COLUMN_DATE = "datum";

    public static final String COLUMN_ANZAHL = "anzahl";
    public static final String COLUMN_SCHNITT_PUNKTE = "schnitt_punkte";
    public static final String COLUMN_SCHNITT_NOTEN = "schnitt_noten";
    public static final String COLUMN_PROZENT_UNGENUEGEND = "prozent_ungenuegend";

    public static final String COLUMN_P15 = "p15";
    public static final String COLUMN_P14 = "p14";
    public static final String COLUMN_P13 = "p13";
    public static final String COLUMN_P12 = "p12";
    public static final String COLUMN_P11 = "p11";
    public static final String COLUMN_P10 = "p10";
    public static final String COLUMN_P9 = "p9";
    public static final String COLUMN_P8 = "p8";
    public static final String COLUMN_P7 = "p7";
    public static final String COLUMN_P6 = "p6";
    public static final String COLUMN_P5 = "p5";
    public static final String COLUMN_P4 = "p4";
    public static final String COLUMN_P3 = "p3";
    public static final String COLUMN_P2 = "p2";
    public static final String COLUMN_P1 = "p1";
    public static final String COLUMN_P0 = "p0";

    public static final String COLUMN_N6 = "n6";
    public static final String COLUMN_N5 = "n5";
    public static final String COLUMN_N4 = "n4";
    public static final String COLUMN_N3 = "n3";
    public static final String COLUMN_N2 = "n2";
    public static final String COLUMN_N1 = "n1";


    // todo hier variablen hinzufügen
    public static final int COL_NO_ID = 0;
    public static final int COL_NO_KLAUSURNAME = 1;
    public static final int COL_NO_KURS = 2;
    public static final int COL_NO_SEMESTER = 3;
    public static final int COL_NO_INFO = 4;
    public static final int COL_NO_MODE = 5;
    public static final int COL_NO_DATE = 6;

    public static final int COL_NO_ANZAHL = 7;
    public static final int COL_NO_SCHNITT_PUNKTE = 8;
    public static final int COL_NO_SCHNITT_NOTEN = 9;
    public static final int COL_NO_PROZENT_UNGENUEGEND = 10;

    public static final int COL_NO_P15 = 11;
    public static final int COL_NO_P14 = 12;
    public static final int COL_NO_P13 = 13;
    public static final int COL_NO_P12 = 14;
    public static final int COL_NO_P11 = 15;
    public static final int COL_NO_P10 = 16;
    public static final int COL_NO_P9 = 17;
    public static final int COL_NO_P8 = 18;
    public static final int COL_NO_P7 = 19;
    public static final int COL_NO_P6 = 20;
    public static final int COL_NO_P5 = 21;
    public static final int COL_NO_P4 = 22;
    public static final int COL_NO_P3 = 23;
    public static final int COL_NO_P2 = 24;
    public static final int COL_NO_P1 = 25;
    public static final int COL_NO_P0 = 26;

    public static final int COL_NO_N6 = 27;
    public static final int COL_NO_N5 = 28;
    public static final int COL_NO_N4 = 29;
    public static final int COL_NO_N3 = 30;
    public static final int COL_NO_N2 = 31;
    public static final int COL_NO_N1 = 32;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "klausuren.db";

    // todo hier variablen hinzufügen
    public static final String[] TABLE_COLUMNS =
            new String[]{COLUMN_ID,
                    COLUMN_KLAUSURNAME,
                    COLUMN_KURS,
                    COLUMN_SEMESTER,
                    COLUMN_INFO,
                    COLUMN_MODE,
                    COLUMN_DATE,
                    COLUMN_ANZAHL,
                    COLUMN_SCHNITT_PUNKTE,
                    COLUMN_SCHNITT_NOTEN,
                    COLUMN_PROZENT_UNGENUEGEND,
                    COLUMN_P15,
                    COLUMN_P14,
                    COLUMN_P13,
                    COLUMN_P12,
                    COLUMN_P11,
                    COLUMN_P10,
                    COLUMN_P9,
                    COLUMN_P8,
                    COLUMN_P7,
                    COLUMN_P6,
                    COLUMN_P5,
                    COLUMN_P4,
                    COLUMN_P3,
                    COLUMN_P2,
                    COLUMN_P1,
                    COLUMN_P0,
                    COLUMN_N6,
                    COLUMN_N5,
                    COLUMN_N4,
                    COLUMN_N3,
                    COLUMN_N2,
                    COLUMN_N1};

    // todo hier variablen hinzufügen
    private static final String INITIAL_SCHEMA =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    COLUMN_KLAUSURNAME + " TEXT ," +
                    COLUMN_KURS + " TEXT ," +
                    COLUMN_SEMESTER + " TEXT ," +
                    COLUMN_INFO + " TEXT ," +
                    COLUMN_MODE + " TEXT ," +
                    COLUMN_DATE + " TEXT ," +
                    COLUMN_ANZAHL + " INTEGER ," +
                    COLUMN_SCHNITT_PUNKTE + " DOUBLE ," +
                    COLUMN_SCHNITT_NOTEN + " DOUBLE ," +
                    COLUMN_PROZENT_UNGENUEGEND + " DOUBLE ," +
                    COLUMN_P15 + " INTEGER ," +
                    COLUMN_P14 + " INTEGER ," +
                    COLUMN_P13 + " INTEGER ," +
                    COLUMN_P12 + " INTEGER ," +
                    COLUMN_P11 + " INTEGER ," +
                    COLUMN_P10 + " INTEGER ," +
                    COLUMN_P9 + " INTEGER ," +
                    COLUMN_P8 + " INTEGER ," +
                    COLUMN_P7 + " INTEGER ," +
                    COLUMN_P6 + " INTEGER ," +
                    COLUMN_P5 + " INTEGER ," +
                    COLUMN_P4 + " INTEGER ," +
                    COLUMN_P3 + " INTEGER ," +
                    COLUMN_P2 + " INTEGER ," +
                    COLUMN_P1 + " INTEGER ," +
                    COLUMN_P0 + " INTEGER ," +
                    COLUMN_N6 + " INTEGER ," +
                    COLUMN_N5 + " INTEGER ," +
                    COLUMN_N4 + " INTEGER ," +
                    COLUMN_N3 + " INTEGER ," +
                    COLUMN_N2 + " INTEGER ," +
                    COLUMN_N1 + " INTEGER " + ");";

    public MyDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INITIAL_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Delete klausur from the database via klausurName
    public void deleteKlausur(String klausurName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_KLAUSURNAME + "=\"" + klausurName + "\";");
    }

    // Hier evtl. eigene Aufräumarbeiten durchführen
    @Override
    public synchronized void close() {
        SQLiteDatabase db = getWritableDatabase();
        if(db != null) {
            db.close();
        }
        super.close();
    }
}
