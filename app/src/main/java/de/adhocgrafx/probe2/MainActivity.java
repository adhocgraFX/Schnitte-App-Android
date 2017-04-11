package de.adhocgrafx.probe2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static de.adhocgrafx.probe2.Berechnungen.anzahl;
import static de.adhocgrafx.probe2.Berechnungen.klausurenAnzahlBerechnen;
import static de.adhocgrafx.probe2.Berechnungen.noten;
import static de.adhocgrafx.probe2.Berechnungen.notenInstanziieren;
import static de.adhocgrafx.probe2.Berechnungen.notenSchnittBerechnen;
import static de.adhocgrafx.probe2.Berechnungen.notenSynchronisieren;
import static de.adhocgrafx.probe2.Berechnungen.notenschnitt;
import static de.adhocgrafx.probe2.Berechnungen.punkte;
import static de.adhocgrafx.probe2.Berechnungen.punkteInstanziieren;
import static de.adhocgrafx.probe2.Berechnungen.punkteSchnittBerechnen;
import static de.adhocgrafx.probe2.Berechnungen.punkteschnitt;
import static de.adhocgrafx.probe2.Berechnungen.schulaufgabenAnzahlBerechnen;
import static de.adhocgrafx.probe2.Berechnungen.tempBerechnungenNoten;
import static de.adhocgrafx.probe2.Berechnungen.tempBerechnungenPunkte;
import static de.adhocgrafx.probe2.Berechnungen.tempnotensumme;
import static de.adhocgrafx.probe2.Berechnungen.temppunktesumme;
import static de.adhocgrafx.probe2.Berechnungen.tempungenuegend;
import static de.adhocgrafx.probe2.Berechnungen.ungenuegend;
import static de.adhocgrafx.probe2.Berechnungen.ungenuegendBerechnen;
import static de.adhocgrafx.probe2.MyDBHelper.COLUMN_KLAUSURNAME;
import static de.adhocgrafx.probe2.MyDBHelper.TABLE_NAME;
import static de.adhocgrafx.probe2.R.id.textErgebnisNoten;
import static de.adhocgrafx.probe2.R.id.textErgebnisPunkte;
import static java.math.RoundingMode.DOWN;


public class MainActivity extends AppCompatActivity {

    private static SQLiteDatabase myDB;
    private MyDBHelper myDBHelper;

    final Context context = this;
    public Klausur tempPruefung;
    public ContentValues myValues;
    public Button btnSpP, btnSpN;
    public boolean gespeichert;

    // TODO ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this);
        myDB = myDBHelper.getWritableDatabase();

        // Schnitte allgemeiner reset
        punkteInstanziieren();
        notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        // allgemeiner reset
                        punkteInstanziieren();
                        notenInstanziieren();

                        // punkte-anzahlen zu noten-anzahlen
                        notenSynchronisieren();

                        // reset der eingabefelder
                        resetInputNoten();

                    case 1:
                        // allgemeiner reset
                        punkteInstanziieren();
                        notenInstanziieren();

                        // punkte-anzahlen zu noten-anzahlen
                        notenSynchronisieren();

                        // reset der eingabefelder
                        resetInputPunkte();

                    case 2:
                        // todo liste aktualisieren
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:

                    case 1:

                    case 2:
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        // allgemeiner reset
                        punkteInstanziieren();
                        notenInstanziieren();

                        // punkte-anzahlen zu noten-anzahlen
                        notenSynchronisieren();

                        // reset der eingabefelder
                        resetInputNoten();

                    case 1:
                        // allgemeiner reset
                        punkteInstanziieren();
                        notenInstanziieren();

                        // punkte-anzahlen zu noten-anzahlen
                        notenSynchronisieren();

                        // reset der eingabefelder
                        resetInputPunkte();

                    case 2:
                        // todo liste aktualisieren
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }


    // TODO PAGER & TABS
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NotenFragment();
                case 1:
                    return new PunkteFragment();
                case 2:
                    return new ErgebnisseFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.Noten);
                case 1:
                    return getString(R.string.Punkte);
                case 2:
                    return getString(R.string.Ergebnisse);
                default:
                    return null;
            }
        }
    }


    // TODO LIST & DATABASE ACTIONS
    public static List<String> getKlausurenErgebnisse() {
        List<String> tempklausuren = new ArrayList<>();

        Cursor cursor = myDB.query(TABLE_NAME,
                MyDBHelper.TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tempklausurinfo1 = "";
            String tempklausurinfo2 = "";

            tempklausurinfo1 = cursor.getString(MyDBHelper.COL_NO_KLAUSURNAME) + " | " +
                    cursor.getString(MyDBHelper.COL_NO_INFO) + "\n" +
                    cursor.getString(MyDBHelper.COL_NO_KURS) + " | " +
                    cursor.getString(MyDBHelper.COL_NO_SEMESTER) + " | " +
                    cursor.getString(MyDBHelper.COL_NO_DATE) + "\n";

            if (cursor.getString(MyDBHelper.COL_NO_MODE).equals("klausur")) {
                tempklausurinfo2 = cursor.getInt(MyDBHelper.COL_NO_ANZAHL) + " | " +
                        formatErgebnis(cursor.getDouble(MyDBHelper.COL_NO_SCHNITT_PUNKTE)) + " | " +
                        formatErgebnis(cursor.getDouble(MyDBHelper.COL_NO_SCHNITT_NOTEN)) + " | " +
                        formatErgebnis(cursor.getDouble(MyDBHelper.COL_NO_PROZENT_UNGENUEGEND)) + "%";
            } else {
                tempklausurinfo2 = cursor.getInt(MyDBHelper.COL_NO_ANZAHL) + " | " +
                        formatErgebnis(cursor.getDouble(MyDBHelper.COL_NO_SCHNITT_NOTEN)) + " | " +
                        formatErgebnis(cursor.getDouble(MyDBHelper.COL_NO_PROZENT_UNGENUEGEND)) + "%";
            }
            tempklausuren.add(tempklausurinfo1 + tempklausurinfo2);

            cursor.moveToNext();
        }

        cursor.close();
        return tempklausuren;
    }

    public static List<String> getKlausurenInfo() {
        List<String> tempklausuren = new ArrayList<>();

        Cursor cursor = myDB.query(TABLE_NAME,
                MyDBHelper.TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tempklausur = cursor.getString(MyDBHelper.COL_NO_KLAUSURNAME) + "\n" +
                    cursor.getString(MyDBHelper.COL_NO_INFO);

            tempklausuren.add(tempklausur);

            cursor.moveToNext();
        }

        cursor.close();
        return tempklausuren;
    }

    // fragment list
    public static Klausur myListClick(int position) {

        Cursor cursor = myDB.rawQuery(
                "select _id, klausur_name, kurs, semester, info, mode, datum, " +
                        "anzahl, schnitt_punkte, schnitt_noten, prozent_ungenuegend, " +
                        "p15, p14, p13, p12, p11, p10, p9, p8, p7, p6, p5, p4, p3, p2, p1, p0, " +
                        "n6, n5, n4, n3, n2, n1 " +
                        "from klausuren", null);

        cursor.moveToPosition(position);

        final Klausur tempklausur = new Klausur();

        tempklausur.klausurName = cursor.getString(MyDBHelper.COL_NO_KLAUSURNAME);
        tempklausur.kurs = cursor.getString(MyDBHelper.COL_NO_KURS);
        tempklausur.semester = cursor.getString(MyDBHelper.COL_NO_SEMESTER);
        tempklausur.info = cursor.getString(MyDBHelper.COL_NO_INFO);
        tempklausur.mode = cursor.getString(MyDBHelper.COL_NO_MODE);
        tempklausur.datum = cursor.getString(MyDBHelper.COL_NO_DATE);
        tempklausur.anzahl = cursor.getInt(MyDBHelper.COL_NO_ANZAHL);
        tempklausur.schnittNoten = cursor.getDouble(MyDBHelper.COL_NO_SCHNITT_NOTEN);
        tempklausur.schnittPunkte = cursor.getDouble(MyDBHelper.COL_NO_SCHNITT_PUNKTE);
        tempklausur.prozentUngenuegend = cursor.getDouble(MyDBHelper.COL_NO_PROZENT_UNGENUEGEND);

        tempklausur.p15 = cursor.getInt(MyDBHelper.COL_NO_P15);
        tempklausur.p14 = cursor.getInt(MyDBHelper.COL_NO_P14);
        tempklausur.p13 = cursor.getInt(MyDBHelper.COL_NO_P13);
        tempklausur.p12 = cursor.getInt(MyDBHelper.COL_NO_P12);
        tempklausur.p11 = cursor.getInt(MyDBHelper.COL_NO_P11);
        tempklausur.p10 = cursor.getInt(MyDBHelper.COL_NO_P10);
        tempklausur.p9 = cursor.getInt(MyDBHelper.COL_NO_P9);
        tempklausur.p8 = cursor.getInt(MyDBHelper.COL_NO_P8);
        tempklausur.p7 = cursor.getInt(MyDBHelper.COL_NO_P7);
        tempklausur.p6 = cursor.getInt(MyDBHelper.COL_NO_P6);
        tempklausur.p5 = cursor.getInt(MyDBHelper.COL_NO_P5);
        tempklausur.p4 = cursor.getInt(MyDBHelper.COL_NO_P4);
        tempklausur.p3 = cursor.getInt(MyDBHelper.COL_NO_P3);
        tempklausur.p2 = cursor.getInt(MyDBHelper.COL_NO_P2);
        tempklausur.p1 = cursor.getInt(MyDBHelper.COL_NO_P1);
        tempklausur.p0 = cursor.getInt(MyDBHelper.COL_NO_P0);

        tempklausur.n6 = cursor.getInt(MyDBHelper.COL_NO_N6);
        tempklausur.n5 = cursor.getInt(MyDBHelper.COL_NO_N5);
        tempklausur.n4 = cursor.getInt(MyDBHelper.COL_NO_N4);
        tempklausur.n3 = cursor.getInt(MyDBHelper.COL_NO_N3);
        tempklausur.n2 = cursor.getInt(MyDBHelper.COL_NO_N2);
        tempklausur.n1 = cursor.getInt(MyDBHelper.COL_NO_N1);

        cursor.close();

        return tempklausur;
    }

    // fragment list
    public static void myKlausurDelete(int position) {
        Cursor cursor = myDB.rawQuery(
                "select _id, klausur_name " + "from klausuren", null);

        cursor.moveToPosition(position);

        final Klausur delklausur = new Klausur();

        delklausur._id = cursor.getInt(MyDBHelper.COL_NO_ID);
        delklausur.klausurName = cursor.getString(MyDBHelper.COL_NO_KLAUSURNAME);

        cursor.close();

        myDB.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_KLAUSURNAME + "=\"" + delklausur.klausurName + "\";");
    }

    // fragment list
    public static void myKlausurUpdate(ContentValues myValues, int position) {
        Cursor cursor = myDB.rawQuery(
                "select _id, klausur_name " + "from klausuren", null);

        cursor.moveToPosition(position);

        final Klausur updateklausur = new Klausur();

        updateklausur._id = cursor.getInt(MyDBHelper.COL_NO_ID);
        updateklausur.klausurName = cursor.getString(MyDBHelper.COL_NO_KLAUSURNAME);

        cursor.close();

        myDB.update(TABLE_NAME, myValues, "klausur_name = ?", new String[]{updateklausur.klausurName});
    }

    // TODO MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.change_preferences) {
            startActivity(new Intent(this, ChangePreferences.class));

            return true;
        }

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            // custom about dialog
            LayoutInflater myInflater = LayoutInflater.from(this);
            View addView = myInflater.inflate(R.layout.about_dialog, null);

            final Activity activity = this;

            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setView(addView)

                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    // Nothing to do here
                                }
                            })
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Todo Speichern und database methoden
    /**
     * This method is called when the speichern button is clicked.
     */
    public void speicherNoten(View view) {

        // speichern teil 1
        myValues = new ContentValues();

        myValues.put(MyDBHelper.COLUMN_MODE, tempPruefung.getMode());
        myValues.put(MyDBHelper.COLUMN_ANZAHL, tempPruefung.getAnzahl());
        myValues.put(MyDBHelper.COLUMN_SCHNITT_NOTEN, tempPruefung.getSchnittNoten());
        myValues.put(MyDBHelper.COLUMN_PROZENT_UNGENUEGEND, tempPruefung.getProzentUngenuegend());
        myValues.put(MyDBHelper.COLUMN_N1, tempPruefung.getN1());
        myValues.put(MyDBHelper.COLUMN_N2, tempPruefung.getN2());
        myValues.put(MyDBHelper.COLUMN_N3, tempPruefung.getN3());
        myValues.put(MyDBHelper.COLUMN_N4, tempPruefung.getN4());
        myValues.put(MyDBHelper.COLUMN_N5, tempPruefung.getN5());
        myValues.put(MyDBHelper.COLUMN_N6, tempPruefung.getN6());

        addKlausur(myValues);
    }

    /**
     * This method is called when the speichern button is clicked.
     */
    public void speicherPunkte(View view) {

        // speichern teil 1
        myValues = new ContentValues();

        myValues.put(MyDBHelper.COLUMN_MODE, tempPruefung.getMode());
        myValues.put(MyDBHelper.COLUMN_ANZAHL, tempPruefung.getAnzahl());
        myValues.put(MyDBHelper.COLUMN_SCHNITT_PUNKTE, tempPruefung.getSchnittPunkte());
        myValues.put(MyDBHelper.COLUMN_SCHNITT_NOTEN, tempPruefung.getSchnittNoten());
        myValues.put(MyDBHelper.COLUMN_PROZENT_UNGENUEGEND, tempPruefung.getProzentUngenuegend());
        myValues.put(MyDBHelper.COLUMN_P15, tempPruefung.getP15());
        myValues.put(MyDBHelper.COLUMN_P14, tempPruefung.getP14());
        myValues.put(MyDBHelper.COLUMN_P13, tempPruefung.getP13());
        myValues.put(MyDBHelper.COLUMN_P12, tempPruefung.getP12());
        myValues.put(MyDBHelper.COLUMN_P11, tempPruefung.getP11());
        myValues.put(MyDBHelper.COLUMN_P10, tempPruefung.getP10());
        myValues.put(MyDBHelper.COLUMN_P9, tempPruefung.getP9());
        myValues.put(MyDBHelper.COLUMN_P8, tempPruefung.getP8());
        myValues.put(MyDBHelper.COLUMN_P7, tempPruefung.getP7());
        myValues.put(MyDBHelper.COLUMN_P6, tempPruefung.getP6());
        myValues.put(MyDBHelper.COLUMN_P5, tempPruefung.getP5());
        myValues.put(MyDBHelper.COLUMN_P4, tempPruefung.getP4());
        myValues.put(MyDBHelper.COLUMN_P3, tempPruefung.getP3());
        myValues.put(MyDBHelper.COLUMN_P2, tempPruefung.getP2());
        myValues.put(MyDBHelper.COLUMN_P1, tempPruefung.getP1());
        myValues.put(MyDBHelper.COLUMN_P0, tempPruefung.getP0());

        addKlausur(myValues);
    }

    private void addKlausur(ContentValues values) {

        LayoutInflater myInflater = LayoutInflater.from(this);
        View addView = myInflater.inflate(R.layout.add_klausur, null);

        final DialogWrapper myWrapper = new DialogWrapper(addView);
        final Activity activity = this;

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String defaultKurseValue = "1ku1,1ku2,1ku3,2ku1,2ku2,2ku3";
        String defaultSemesterValue = "11/1,11/2,12/1,12/2,2016/17";

        // get spinner items from preferences
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String kurse = myPrefs.getString("kurse", defaultKurseValue);
        String[] kurs_array = kurse.split(",");
        for (int i = 0; i < kurs_array.length; i++) {
            kurs_array[i] = kurs_array[i].trim();
        }
        String semester = myPrefs.getString("semester", defaultSemesterValue);
        String[] semester_array = semester.split(",");
        for (int i = 0; i < semester_array.length; i++) {
            semester_array[i] = semester_array[i].trim();
        }

        // add Spinner items
        final Spinner spinnerKurs = (Spinner) addView.findViewById(R.id.kurs);
        ArrayAdapter<String> adapterKurs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kurs_array);
        adapterKurs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKurs.setAdapter(adapterKurs);
        spinnerKurs.setOnItemSelectedListener(new SpinnerActivity());

        final Spinner spinnerSemester = (Spinner) addView.findViewById(R.id.semester);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semester_array);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapterSemester);
        spinnerSemester.setOnItemSelectedListener(new SpinnerActivity());

        final TextView datumText = (TextView) addView.findViewById(R.id.txtDate);
        datumText.setText(null);

        Button button = (Button) addView.findViewById(R.id.btnDatum);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // DatePickerDialog anzeigen zur Auswahl des Geburtsdatums
                final DatePickerDialog.OnDateSetListener listener;

                listener = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int geburtstag = view.getDayOfMonth();
                        int geburtsmonat = view.getMonth();
                        int geburtsjahr = view.getYear();
                        Calendar calendar = new GregorianCalendar(geburtsjahr, geburtsmonat, geburtstag);
                        String str = dateFormat.format(calendar.getTime());
                        datumText.setText(str);
                    }
                };

                // Dialog erzeugen und Datum auf heute setzen
                Calendar c = Calendar.getInstance();
                int jahr = c.get(Calendar.YEAR);
                int monat = c.get(Calendar.MONTH);
                int tag = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(activity, listener, jahr, monat, tag);
                dialog.setCancelable(false);
                dialog.setOwnerActivity(activity);
                dialog.show();
            }
        });

        new AlertDialog.Builder(this)
                .setTitle(R.string.add_klausur_title)
                .setView(addView)

                .setPositiveButton(R.string.speichern,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                gespeichert = insertKlausur(myWrapper, myValues);

                                if (!gespeichert) {
                                    // toasten
                                    CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Geben Sie bitte einen Prüfungsnamen ein.";
                                    int duration2 = Toast.LENGTH_LONG;
                                    Toast toast2 = Toast.makeText(context, text2, duration2);
                                    toast2.show();
                                } else {
                                    // toasten
                                    CharSequence text = "Die Prüfung wurde erfolgreich gespeichert.";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    btnSpP = (Button) findViewById(R.id.btnSpeichernPunkte);
                                    btnSpP.setEnabled(false);
                                    btnSpN = (Button) findViewById(R.id.btnSpeichernNoten);
                                    btnSpN.setEnabled(false);
                                }
                            }
                        })

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Nothing to do here
                            }
                        })

                .show();
    }

    private boolean insertKlausur(DialogWrapper wrapper, ContentValues values) {

        if (wrapper.getKlausur().isEmpty()) {
            // nothing is saved
            gespeichert = false;

        } else {
            // endgültig speichern
            myValues.put(MyDBHelper.COLUMN_KLAUSURNAME, wrapper.getKlausur());
            myValues.put(MyDBHelper.COLUMN_KURS, wrapper.getKurs());
            myValues.put(MyDBHelper.COLUMN_SEMESTER, wrapper.getSemester());
            myValues.put(MyDBHelper.COLUMN_INFO, wrapper.getInfo());
            myValues.put(MyDBHelper.COLUMN_DATE, wrapper.getDatum());

            myDB.insert(TABLE_NAME, COLUMN_KLAUSURNAME, myValues);

            gespeichert = true;
        }

        return gespeichert;
    }


    // TODO BERECHNUNGEN

    /**
     * Methode: punkte-anzahlen einlesen und punkte-array [i][1] damit füllen
     */
    private void getPunkteAnzahlen() {
        // Punkte in array einlesen
        EditText p15 = (EditText) findViewById(R.id.editP15);
        EditText p14 = (EditText) findViewById(R.id.editP14);
        EditText p13 = (EditText) findViewById(R.id.editP13);
        EditText p12 = (EditText) findViewById(R.id.editP12);
        EditText p11 = (EditText) findViewById(R.id.editP11);
        EditText p10 = (EditText) findViewById(R.id.editP10);
        EditText p9 = (EditText) findViewById(R.id.editP9);
        EditText p8 = (EditText) findViewById(R.id.editP8);
        EditText p7 = (EditText) findViewById(R.id.editP7);
        EditText p6 = (EditText) findViewById(R.id.editP6);
        EditText p5 = (EditText) findViewById(R.id.editP5);
        EditText p4 = (EditText) findViewById(R.id.editP4);
        EditText p3 = (EditText) findViewById(R.id.editP3);
        EditText p2 = (EditText) findViewById(R.id.editP2);
        EditText p1 = (EditText) findViewById(R.id.editP1);
        EditText p0 = (EditText) findViewById(R.id.editP0);

        try {
            punkte[15][1] = Integer.parseInt(p15.getText().toString());
        } catch (NumberFormatException e) {
            punkte[15][1] = 0;
        }
        try {
            punkte[14][1] = Integer.parseInt(p14.getText().toString());
        } catch (NumberFormatException e) {
            punkte[14][1] = 0;
        }
        try {
            punkte[13][1] = Integer.parseInt(p13.getText().toString());
        } catch (NumberFormatException e) {
            punkte[13][1] = 0;
        }
        try {
            punkte[12][1] = Integer.parseInt(p12.getText().toString());
        } catch (NumberFormatException e) {
            punkte[12][1] = 0;
        }
        try {
            punkte[11][1] = Integer.parseInt(p11.getText().toString());
        } catch (NumberFormatException e) {
            punkte[11][1] = 0;
        }
        try {
            punkte[10][1] = Integer.parseInt(p10.getText().toString());
        } catch (NumberFormatException e) {
            punkte[10][1] = 0;
        }
        try {
            punkte[9][1] = Integer.parseInt(p9.getText().toString());
        } catch (NumberFormatException e) {
            punkte[9][1] = 0;
        }
        try {
            punkte[8][1] = Integer.parseInt(p8.getText().toString());
        } catch (NumberFormatException e) {
            punkte[8][1] = 0;
        }
        try {
            punkte[7][1] = Integer.parseInt(p7.getText().toString());
        } catch (NumberFormatException e) {
            punkte[7][1] = 0;
        }
        try {
            punkte[6][1] = Integer.parseInt(p6.getText().toString());
        } catch (NumberFormatException e) {
            punkte[6][1] = 0;
        }
        try {
            punkte[5][1] = Integer.parseInt(p5.getText().toString());
        } catch (NumberFormatException e) {
            punkte[5][1] = 0;
        }
        try {
            punkte[4][1] = Integer.parseInt(p4.getText().toString());
        } catch (NumberFormatException e) {
            punkte[4][1] = 0;
        }
        try {
            punkte[3][1] = Integer.parseInt(p3.getText().toString());
        } catch (NumberFormatException e) {
            punkte[3][1] = 0;
        }
        try {
            punkte[2][1] = Integer.parseInt(p2.getText().toString());
        } catch (NumberFormatException e) {
            punkte[2][1] = 0;
        }
        try {
            punkte[1][1] = Integer.parseInt(p1.getText().toString());
        } catch (NumberFormatException e) {
            punkte[1][1] = 0;
        }
        try {
            punkte[0][1] = Integer.parseInt(p0.getText().toString());
        } catch (NumberFormatException e) {
            punkte[0][1] = 0;
        }
    }

    /**
     * Methode: noten-anzahlen einlesen und noten-array [i][1] damit füllen
     */
    private void getNotenAnzahlen() {
        // Noten in array einlesen
        EditText n1 = (EditText) findViewById(R.id.editN1);
        EditText n2 = (EditText) findViewById(R.id.editN2);
        EditText n3 = (EditText) findViewById(R.id.editN3);
        EditText n4 = (EditText) findViewById(R.id.editN4);
        EditText n5 = (EditText) findViewById(R.id.editN5);
        EditText n6 = (EditText) findViewById(R.id.editN6);

        try {
            noten[1][1] = Integer.parseInt(n1.getText().toString());
        } catch (NumberFormatException e) {
            noten[1][1] = 0;
        }
        try {
            noten[2][1] = Integer.parseInt(n2.getText().toString());
        } catch (NumberFormatException e) {
            noten[2][1] = 0;
        }
        try {
            noten[3][1] = Integer.parseInt(n3.getText().toString());
        } catch (NumberFormatException e) {
            noten[3][1] = 0;
        }
        try {
            noten[4][1] = Integer.parseInt(n4.getText().toString());
        } catch (NumberFormatException e) {
            noten[4][1] = 0;
        }
        try {
            noten[5][1] = Integer.parseInt(n5.getText().toString());
        } catch (NumberFormatException e) {
            noten[5][1] = 0;
        }
        try {
            noten[6][1] = Integer.parseInt(n6.getText().toString());
        } catch (NumberFormatException e) {
            noten[6][1] = 0;
        }
    }

    /**
     * This method is called when the berechnen button is clicked.
     */
    public void berechneNoten(View view) {
        // berechnen
        notenBerechnen();
    }

    /**
     * This method is called when the berechnen button is clicked.
     */
    public void berechnePunkte(View view) {
        // berechnen
        punkteBerechnen();
    }

    /**
     * Method action punkte Berechnen
     */
    private void punkteBerechnen() {

        // punkte-anzahlen einlesen und punkte-array [i][1] damit füllen
        getPunkteAnzahlen();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();

        // temporäre Berechnungen
        tempBerechnungenPunkte();

        // Berechnungen
        klausurenAnzahlBerechnen();

        if (klausurenAnzahlBerechnen() != 0) {
            punkteSchnittBerechnen(temppunktesumme, anzahl);
            notenSchnittBerechnen(tempnotensumme, anzahl);
            ungenuegendBerechnen(tempungenuegend, anzahl);

            // neues Objekt
            Schnitte schnittPunkte = new Schnitte(anzahl, punkteschnitt, notenschnitt, ungenuegend);

            // Ergebnis
            TextView textE = (TextView) findViewById(textErgebnisPunkte);
            textE.setText("");
            textE.setText("Anzahl der Arbeiten: " + schnittPunkte.anzahl + "\n" +
                    "Punkteschnitt: " + formatErgebnis(schnittPunkte.schnittPunkte) + "\n" +
                    "Notenschnitt: " + formatErgebnis(schnittPunkte.schnittNoten) + "\n" +
                    "P3 - P0: " + formatErgebnis(schnittPunkte.prozentUngenuegend) + "%");

            // für das Speichern vorbereiten
            tempPruefung = new Klausur();

            tempPruefung.setMode("klausur");
            tempPruefung.setAnzahl(schnittPunkte.anzahl);
            tempPruefung.setSchnittPunkte(schnittPunkte.schnittPunkte);
            tempPruefung.setSchnittNoten(schnittPunkte.schnittNoten);
            tempPruefung.setProzentUngenuegend(schnittPunkte.prozentUngenuegend);
            tempPruefung.setP15(punkte[15][1]);
            tempPruefung.setP14(punkte[14][1]);
            tempPruefung.setP13(punkte[13][1]);
            tempPruefung.setP12(punkte[12][1]);
            tempPruefung.setP11(punkte[11][1]);
            tempPruefung.setP10(punkte[10][1]);
            tempPruefung.setP9(punkte[9][1]);
            tempPruefung.setP8(punkte[8][1]);
            tempPruefung.setP7(punkte[7][1]);
            tempPruefung.setP6(punkte[6][1]);
            tempPruefung.setP5(punkte[5][1]);
            tempPruefung.setP4(punkte[4][1]);
            tempPruefung.setP3(punkte[3][1]);
            tempPruefung.setP2(punkte[2][1]);
            tempPruefung.setP1(punkte[1][1]);
            tempPruefung.setP0(punkte[0][1]);

            btnSpP = (Button) findViewById(R.id.btnSpeichernPunkte);
            btnSpP.setEnabled(true);

        } else if (klausurenAnzahlBerechnen() == 0) {
            // kein Ergebnis
            TextView textE = (TextView) findViewById(textErgebnisPunkte);
            textE.setText("");
            textE.setText(R.string.errorText);
        }

        // allgemeiner reset
        punkteInstanziieren();
        notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();
    }

    /**
     * Method action noten Berechnen
     */
    private void notenBerechnen() {

        // noten-anzahlen einlesen und noten-array [i][1] damit füllen
        getNotenAnzahlen();

        // temporäre Berechnungen
        tempBerechnungenNoten();

        // Berechnungen
        schulaufgabenAnzahlBerechnen();

        if (schulaufgabenAnzahlBerechnen() != 0) {
            notenSchnittBerechnen(tempnotensumme, anzahl);
            ungenuegendBerechnen(tempungenuegend, anzahl);

            // neues Objekt
            Schnitte schnittNoten = new Schnitte(anzahl, notenschnitt, ungenuegend);

            // Ergebnis
            TextView textE = (TextView) findViewById(textErgebnisNoten);
            textE.setText("");
            textE.setText("Anzahl der Arbeiten: " + schnittNoten.anzahl + "\n" +
                    "Notenschnitt: " + formatErgebnis(schnittNoten.schnittNoten) + "\n" +
                    "N5 - N6: " + formatErgebnis(schnittNoten.prozentUngenuegend) + "%");

            // für das Speichern vorbereiten
            tempPruefung = new Klausur();

            tempPruefung.setMode("schulaufgabe");
            tempPruefung.setAnzahl(schnittNoten.anzahl);
            tempPruefung.setSchnittNoten(schnittNoten.schnittNoten);
            tempPruefung.setProzentUngenuegend(schnittNoten.prozentUngenuegend);

            tempPruefung.setN1(noten[1][1]);
            tempPruefung.setN2(noten[2][1]);
            tempPruefung.setN3(noten[3][1]);
            tempPruefung.setN4(noten[4][1]);
            tempPruefung.setN5(noten[5][1]);
            tempPruefung.setN6(noten[6][1]);

            btnSpN = (Button) findViewById(R.id.btnSpeichernNoten);
            btnSpN.setEnabled(true);

        } else if (schulaufgabenAnzahlBerechnen() == 0) {
            // kein Ergebnis
            TextView textE = (TextView) findViewById(textErgebnisNoten);
            textE.setText("");
            textE.setText(R.string.errorText);
        }

        // allgemeiner reset
        punkteInstanziieren();
        notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();
    }

    /**
     * This method is called when the reset button is clicked.
     */
    public void resetNoten(View view) {
        // allgemeiner reset
        punkteInstanziieren();
        notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();

        // reset der eingabefelder
        resetInputNoten();
    }

    private void resetInputNoten() {
        TextView textE = (TextView) findViewById(textErgebnisNoten);
        textE.setText("");

        EditText n1 = (EditText) findViewById(R.id.editN1);
        EditText n2 = (EditText) findViewById(R.id.editN2);
        EditText n3 = (EditText) findViewById(R.id.editN3);
        EditText n4 = (EditText) findViewById(R.id.editN4);
        EditText n5 = (EditText) findViewById(R.id.editN5);
        EditText n6 = (EditText) findViewById(R.id.editN6);

        n1.setText("");
        n2.setText("");
        n3.setText("");
        n4.setText("");
        n5.setText("");
        n6.setText("");

        btnSpN = (Button) findViewById(R.id.btnSpeichernNoten);
        btnSpN.setEnabled(false);
    }

    /**
     * This method is called when the reset button is clicked.
     */
    public void resetPunkte(View view) {
        // allgemeiner reset
        punkteInstanziieren();
        notenInstanziieren();

        // punkte-anzahlen zu noten-anzahlen
        notenSynchronisieren();

        // reset der eingabefelder
        resetInputPunkte();
    }

    private void resetInputPunkte() {
        TextView textE = (TextView) findViewById(textErgebnisPunkte);
        textE.setText("");

        EditText p15 = (EditText) findViewById(R.id.editP15);
        EditText p14 = (EditText) findViewById(R.id.editP14);
        EditText p13 = (EditText) findViewById(R.id.editP13);
        EditText p12 = (EditText) findViewById(R.id.editP12);
        EditText p11 = (EditText) findViewById(R.id.editP11);
        EditText p10 = (EditText) findViewById(R.id.editP10);
        EditText p9 = (EditText) findViewById(R.id.editP9);
        EditText p8 = (EditText) findViewById(R.id.editP8);
        EditText p7 = (EditText) findViewById(R.id.editP7);
        EditText p6 = (EditText) findViewById(R.id.editP6);
        EditText p5 = (EditText) findViewById(R.id.editP5);
        EditText p4 = (EditText) findViewById(R.id.editP4);
        EditText p3 = (EditText) findViewById(R.id.editP3);
        EditText p2 = (EditText) findViewById(R.id.editP2);
        EditText p1 = (EditText) findViewById(R.id.editP1);
        EditText p0 = (EditText) findViewById(R.id.editP0);

        p15.setText("");
        p14.setText("");
        p13.setText("");
        p12.setText("");
        p11.setText("");
        p10.setText("");
        p9.setText("");
        p8.setText("");
        p7.setText("");
        p6.setText("");
        p5.setText("");
        p4.setText("");
        p3.setText("");
        p2.setText("");
        p1.setText("");
        p0.setText("");

        btnSpP = (Button) findViewById(R.id.btnSpeichernPunkte);
        btnSpP.setEnabled(false);
    }

    /**
     * Methode double zahlen auf 2 stellen hinter komma abschneiden, nicht runden
     */
    public static String formatErgebnis(double ergebnis) {
        // double auf 2 stellen hinter komma abschneiden, nicht runden
        NumberFormat numberFormat = new DecimalFormat("0.00");
        numberFormat.setRoundingMode(DOWN);

        // formatiertes ergebnis ausgeben
        return numberFormat.format(ergebnis);
    }

    /**
     * Methode int zahlen als String ausgeben
     */
    public static String formatAnzahl(int ergebnis) {
        // int auf 0 stellen hinter komma abschneiden, nicht runden
        NumberFormat numberFormat = new DecimalFormat("0");
        numberFormat.setRoundingMode(DOWN);

        // formatiertes ergebnis ausgeben
        return numberFormat.format(ergebnis);
    }
}
