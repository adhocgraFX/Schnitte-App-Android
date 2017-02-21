package de.adhocgrafx.probe2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static de.adhocgrafx.probe2.MainActivity.formatAnzahl;
import static de.adhocgrafx.probe2.MainActivity.formatErgebnis;
import static de.adhocgrafx.probe2.MainActivity.getKlausurenErgebnisse;
import static de.adhocgrafx.probe2.R.id.txtDate;
import static de.adhocgrafx.probe2.R.string.n1;

public class ErgebnisseFragment extends ListFragment {

    public ContentValues myValues;
    public Klausur tempklausur = new Klausur();
    public boolean gespeichert;

    static final int INFO_ID = 0;
    static final int EDIT_INFO_ID = 1;
    static final int EDIT_SCHNITT_ID = 2;
    static final int DELETE_ID = 3;

    public ErgebnisseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ergebnisse, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerForContextMenu(getListView());

        displayKlausuren();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // todo ? nötig ?
        displayKlausuren();
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        final Context helpContext = getActivity();

        // kleiner hilfetext als toast, via preferences abstellbar
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(helpContext);
        Boolean hilfe = myPrefs.getBoolean("switch", true);
        if (hilfe) {
            CharSequence text = "Informationen, Bearbeiten und Löschen werden als Kontextmenü angezeigt >> Listeneinträge länger antippen.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(helpContext, text, duration);
            toast.show();
        }
    }

    public void displayKlausuren() {

        List<String> klausurEntries = getKlausurenErgebnisse();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, klausurEntries);

        setListAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, INFO_ID, 0, "Überblick");
        menu.add(0, EDIT_INFO_ID, 0, "Info Bearbeiten");
        menu.add(0, EDIT_SCHNITT_ID, 0, "Schnitt Bearbeiten");
        menu.add(0, DELETE_ID, 0, "Löschen");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        final Context thisContext = getActivity();

        switch (item.getItemId()) {

            case INFO_ID:
                showInfo((int) info.id);
                return true;

            case EDIT_INFO_ID:
                updateKlausur((int) info.id);
                return true;

            case EDIT_SCHNITT_ID:
                updateSchnitt((int) info.id);
                return true;

            case DELETE_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage(R.string.delete_message)
                        .setTitle(R.string.delete_title);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // klausur löschen
                        MainActivity.myKlausurDelete((int) info.id);
                        // refresh
                        displayKlausuren();
                        // toasten
                        CharSequence text = "Die Prüfung wurde gelöscht.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(thisContext, text, duration);
                        toast.show();
                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    // info dialog
    public void showInfo(int position) {

        tempklausur = MainActivity.myListClick(position);

        final Context myContext = getActivity();

        // custom info dialog
        LayoutInflater myInflater = LayoutInflater.from(myContext);
        View infoDialog = myInflater.inflate(R.layout.info_dialog, null);

        TextView txtInfo0 = (TextView) infoDialog.findViewById(R.id.txtInfo0);
        TextView lblInfo0 = (TextView) infoDialog.findViewById(R.id.lblInfo0);
        TextView txtInfo1 = (TextView) infoDialog.findViewById(R.id.txtInfo1);
        TextView lblInfo1 = (TextView) infoDialog.findViewById(R.id.lblInfo1);
        TextView txtInfo2 = (TextView) infoDialog.findViewById(R.id.txtInfo2);
        TextView lblInfo2 = (TextView) infoDialog.findViewById(R.id.lblInfo2);

        lblInfo0.setText("Info:");
        txtInfo0.setText(tempklausur.info);
        txtInfo1.setText(tempklausur.kurs + "\n" + tempklausur.semester + "\n" + tempklausur.datum);
        if (tempklausur.mode.equals("klausur")) {
            lblInfo1.setText("Kurs:" + "\n" + "Semester:" + "\n" + "Datum:");
            lblInfo2.setText("Anzahl:" + "\n" + "Punkteschnitt:" + "\n" + "Notenschnitt:" + "\n" + "P3 - P0:");
            txtInfo2.setText(String.valueOf(tempklausur.anzahl) + "\n" + formatErgebnis(tempklausur.schnittPunkte) + "\n" +
                    formatErgebnis(tempklausur.schnittNoten) + "\n" + formatErgebnis(tempklausur.prozentUngenuegend) + "%");
        }
        else if (tempklausur.mode.equals("schulaufgabe")) {
            lblInfo1.setText("Klasse:" + "\n" + "Schuljahr:" + "\n" + "Datum:");
            lblInfo2.setText("Anzahl:" + "\n" + "Notenschnitt:" + "\n" + "N5 - N6:");
            txtInfo2.setText(String.valueOf(tempklausur.anzahl) + "\n" + formatErgebnis(tempklausur.schnittNoten) + "\n" +
                    formatErgebnis(tempklausur.prozentUngenuegend) + "%");
        }

        new AlertDialog.Builder(myContext)
                .setTitle(tempklausur.klausurName)
                .setView(infoDialog)

                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Nothing to do here
                            }
                        })
                .show();
    }

    // update dialog
    private void updateKlausur(final int position) {

        tempklausur = MainActivity.myListClick(position);

        final Context myContext = getActivity();
        final Activity activity = getActivity();

        LayoutInflater myInflater = LayoutInflater.from(myContext);
        View addView = myInflater.inflate(R.layout.edit_klausur, null);

        final DialogWrapper editWrapper = new DialogWrapper(addView);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        final TextView datumText = (TextView) addView.findViewById(txtDate);
        datumText.setText(null);

        Button button = (Button) addView.findViewById(R.id.btnDatum);
        button.setOnClickListener(new View.OnClickListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(myContext, listener, jahr, monat, tag);
                dialog.setCancelable(false);
                dialog.setOwnerActivity(activity);
                dialog.show();
            }
        });

        EditText klausur_name = (EditText) addView.findViewById(R.id.klausur_name);
        EditText kurs = (EditText) addView.findViewById(R.id.kursFeld);
        EditText semester = (EditText) addView.findViewById(R.id.semesterFeld);
        EditText info = (EditText) addView.findViewById(R.id.info);

        // set edit texte
        klausur_name.setText(tempklausur.klausurName);
        kurs.setText(tempklausur.kurs);
        semester.setText(tempklausur.semester);
        info.setText(tempklausur.info);
        datumText.setText(tempklausur.datum);

        new AlertDialog.Builder(myContext)
                .setTitle(tempklausur.klausurName + " bearbeiten")
                .setView(addView)

                .setPositiveButton(R.string.speichern,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                gespeichert = updateKlausurData(editWrapper, position);

                                if (!gespeichert) {
                                    // toasten
                                    CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Geben Sie bitte einen Prüfungsnamen ein.";
                                    int duration2 = Toast.LENGTH_LONG;
                                    Toast toast2 = Toast.makeText(myContext, text2, duration2);
                                    toast2.show();
                                } else {
                                    // toasten
                                    CharSequence text = "Die Prüfung wurde erfolgreich gespeichert.";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(myContext, text, duration);
                                    toast.show();
                                }
                                // refresh
                                displayKlausuren();
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

    public boolean updateKlausurData(DialogWrapper wrapper, int position) {

        // values einlesen
        myValues = new ContentValues();

        if (wrapper.getKlausur().isEmpty()) {
            // nothing is saved
            gespeichert = false;

        } else {
            // endgültig speichern
            myValues.put(MyDBHelper.COLUMN_KLAUSURNAME, wrapper.getKlausur());
            myValues.put(MyDBHelper.COLUMN_KURS, wrapper.getKursText());
            myValues.put(MyDBHelper.COLUMN_SEMESTER, wrapper.getSemesterText());
            myValues.put(MyDBHelper.COLUMN_INFO, wrapper.getInfo());
            myValues.put(MyDBHelper.COLUMN_DATE, wrapper.getDatum());

            // datenbank update aktivität in MainActivity ausführen
            MainActivity.myKlausurUpdate(myValues, position);

            gespeichert = true;
        }

        return gespeichert;
    }

    // update Schnitt dialog
    private void updateSchnitt(final int position) {

        tempklausur = MainActivity.myListClick(position);

        final Context myContext = getActivity();
        final Activity activity = getActivity();

        LayoutInflater myInflater = LayoutInflater.from(myContext);

        if (tempklausur.mode.equals("klausur")) {

            View addView = myInflater.inflate(R.layout.edit_punkte, null);
            final DialogWrapper punkteWrapper = new DialogWrapper(addView);

            EditText P15 = (EditText) addView.findViewById(R.id.editP15);
            EditText P14 = (EditText) addView.findViewById(R.id.editP14);
            EditText P13 = (EditText) addView.findViewById(R.id.editP13);
            EditText P12 = (EditText) addView.findViewById(R.id.editP12);
            EditText P11 = (EditText) addView.findViewById(R.id.editP11);
            EditText P10 = (EditText) addView.findViewById(R.id.editP10);
            EditText P9 = (EditText) addView.findViewById(R.id.editP9);
            EditText P8 = (EditText) addView.findViewById(R.id.editP8);
            EditText P7 = (EditText) addView.findViewById(R.id.editP7);
            EditText P6 = (EditText) addView.findViewById(R.id.editP6);
            EditText P5 = (EditText) addView.findViewById(R.id.editP5);
            EditText P4 = (EditText) addView.findViewById(R.id.editP4);
            EditText P3 = (EditText) addView.findViewById(R.id.editP3);
            EditText P2 = (EditText) addView.findViewById(R.id.editP2);
            EditText P1 = (EditText) addView.findViewById(R.id.editP1);
            EditText P0 = (EditText) addView.findViewById(R.id.editP0);

            TextView txtPunkteErgebnis = (TextView) addView.findViewById(R.id.textErgebnisPunkte);
            Button btnPunkteBerechnen = (Button) addView.findViewById(R.id.btnBerechnenPunkte);
            Button btnPunkteReset = (Button) addView.findViewById(R.id.btnResetPunkte);

            // set edit texte
            if (tempklausur.p15 != 0) P15.setText(String.valueOf(tempklausur.p15));
            if (tempklausur.p14 != 0) P14.setText(String.valueOf(tempklausur.p14));
            if (tempklausur.p13 != 0) P13.setText(String.valueOf(tempklausur.p13));
            if (tempklausur.p12 != 0) P12.setText(String.valueOf(tempklausur.p12));
            if (tempklausur.p11 != 0) P11.setText(String.valueOf(tempklausur.p11));
            if (tempklausur.p10 != 0) P10.setText(String.valueOf(tempklausur.p10));
            if (tempklausur.p9 != 0) P9.setText(String.valueOf(tempklausur.p9));
            if (tempklausur.p8 != 0) P8.setText(String.valueOf(tempklausur.p8));
            if (tempklausur.p7 != 0) P7.setText(String.valueOf(tempklausur.p7));
            if (tempklausur.p6 != 0) P6.setText(String.valueOf(tempklausur.p6));
            if (tempklausur.p5 != 0) P5.setText(String.valueOf(tempklausur.p5));
            if (tempklausur.p4 != 0) P4.setText(String.valueOf(tempklausur.p4));
            if (tempklausur.p3 != 0) P3.setText(String.valueOf(tempklausur.p3));
            if (tempklausur.p2 != 0) P2.setText(String.valueOf(tempklausur.p2));
            if (tempklausur.p1 != 0) P1.setText(String.valueOf(tempklausur.p1));
            if (tempklausur.p0 != 0) P0.setText(String.valueOf(tempklausur.p0));

            // Todo berechnen fehlt noch!


            new AlertDialog.Builder(myContext)
                    .setTitle(tempklausur.klausurName + " bearbeiten")
                    .setView(addView)

                    .setPositiveButton(R.string.speichern,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    gespeichert = updatePunkteSchnittData(punkteWrapper, position);

                                    if (!gespeichert) {
                                        // toasten
                                        CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Geben Sie bitte einen Prüfungsnamen ein.";
                                        int duration2 = Toast.LENGTH_LONG;
                                        Toast toast2 = Toast.makeText(myContext, text2, duration2);
                                        toast2.show();
                                    } else {
                                        // toasten
                                        CharSequence text = "Die Prüfung wurde erfolgreich gespeichert.";
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(myContext, text, duration);
                                        toast.show();
                                    }
                                    // refresh
                                    displayKlausuren();
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

        else if (tempklausur.mode.equals("schulaufgabe")) {

            View addView = myInflater.inflate(R.layout.edit_noten, null);
            final DialogWrapper notenWrapper = new DialogWrapper(addView);

            EditText N1 = (EditText) addView.findViewById(R.id.editN1);
            EditText N2 = (EditText) addView.findViewById(R.id.editN2);
            EditText N3 = (EditText) addView.findViewById(R.id.editN3);
            EditText N4 = (EditText) addView.findViewById(R.id.editN4);
            EditText N5 = (EditText) addView.findViewById(R.id.editN5);
            EditText N6 = (EditText) addView.findViewById(R.id.editN6);

            TextView txtNotenErgebnis = (TextView) addView.findViewById(R.id.textErgebnisNoten);
            Button btnNotenBerechnen = (Button) addView.findViewById(R.id.btnBerechnenNoten);
            Button btnNotenReset = (Button) addView.findViewById(R.id.btnResetNoten);

            // set edit texte
            if (tempklausur.n1 != 0) N1.setText(String.valueOf(tempklausur.n1));
            if (tempklausur.n2 != 0) N2.setText(String.valueOf(tempklausur.n2));
            if (tempklausur.n3 != 0) N3.setText(String.valueOf(tempklausur.n3));
            if (tempklausur.n4 != 0) N4.setText(String.valueOf(tempklausur.n4));
            if (tempklausur.n5 != 0) N5.setText(String.valueOf(tempklausur.n5));
            if (tempklausur.n6 != 0) N6.setText(String.valueOf(tempklausur.n6));

            // Todo berechnen fehlt noch!


            new AlertDialog.Builder(myContext)
                    .setTitle(tempklausur.klausurName + " bearbeiten")
                    .setView(addView)

                    .setPositiveButton(R.string.speichern,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    gespeichert = updateNotenSchnittData(notenWrapper, position);

                                    if (!gespeichert) {
                                        // toasten
                                        CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Geben Sie bitte einen Prüfungsnamen ein.";
                                        int duration2 = Toast.LENGTH_LONG;
                                        Toast toast2 = Toast.makeText(myContext, text2, duration2);
                                        toast2.show();
                                    } else {
                                        // toasten
                                        CharSequence text = "Die Prüfung wurde erfolgreich gespeichert.";
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(myContext, text, duration);
                                        toast.show();
                                    }
                                    // refresh
                                    displayKlausuren();
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
    }

    public boolean updatePunkteSchnittData(DialogWrapper wrapper, int position) {

        // values einlesen
        myValues = new ContentValues();

        if (wrapper.getKlausur().isEmpty()) {
            // nothing is saved
            gespeichert = false;

        } else {
            // endgültig speichern
            myValues.put(MyDBHelper.COLUMN_P15, wrapper.getP15());
            myValues.put(MyDBHelper.COLUMN_P14, wrapper.getP14());
            myValues.put(MyDBHelper.COLUMN_P13, wrapper.getP13());
            myValues.put(MyDBHelper.COLUMN_P12, wrapper.getP12());
            myValues.put(MyDBHelper.COLUMN_P11, wrapper.getP11());
            myValues.put(MyDBHelper.COLUMN_P10, wrapper.getP10());
            myValues.put(MyDBHelper.COLUMN_P9, wrapper.getP9());
            myValues.put(MyDBHelper.COLUMN_P8, wrapper.getP8());
            myValues.put(MyDBHelper.COLUMN_P7, wrapper.getP7());
            myValues.put(MyDBHelper.COLUMN_P6, wrapper.getP6());
            myValues.put(MyDBHelper.COLUMN_P5, wrapper.getP5());
            myValues.put(MyDBHelper.COLUMN_P4, wrapper.getP4());
            myValues.put(MyDBHelper.COLUMN_P3, wrapper.getP3());
            myValues.put(MyDBHelper.COLUMN_P2, wrapper.getP2());
            myValues.put(MyDBHelper.COLUMN_P1, wrapper.getP1());
            myValues.put(MyDBHelper.COLUMN_P0, wrapper.getP0());

            // todo ergebnis fehlt noch!


            // datenbank update aktivität in MainActivity ausführen
            MainActivity.myKlausurUpdate(myValues, position);

            gespeichert = true;
        }

        return gespeichert;
    }

    public boolean updateNotenSchnittData(DialogWrapper wrapper, int position) {

        // values einlesen
        myValues = new ContentValues();

        if (wrapper.getKlausur().isEmpty()) {
            // nothing is saved
            gespeichert = false;

        } else {
            // endgültig speichern
            myValues.put(MyDBHelper.COLUMN_N1, wrapper.getN1());
            myValues.put(MyDBHelper.COLUMN_N2, wrapper.getN2());
            myValues.put(MyDBHelper.COLUMN_N3, wrapper.getN3());
            myValues.put(MyDBHelper.COLUMN_N4, wrapper.getN4());
            myValues.put(MyDBHelper.COLUMN_N5, wrapper.getN5());
            myValues.put(MyDBHelper.COLUMN_N6, wrapper.getN6());

            // todo ergebnis fehlt noch!


            // datenbank update aktivität in MainActivity ausführen
            MainActivity.myKlausurUpdate(myValues, position);

            gespeichert = true;
        }

        return gespeichert;
    }
}
