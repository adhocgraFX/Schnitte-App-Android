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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.ListFragment;

import java.text.SimpleDateFormat;
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
import static de.adhocgrafx.probe2.MainActivity.formatErgebnis;
import static de.adhocgrafx.probe2.MainActivity.getKlausurenInfo;
import static de.adhocgrafx.probe2.R.id.btnResetNoten;
import static de.adhocgrafx.probe2.R.id.txtDate;

public class ErgebnisseFragment extends ListFragment implements View.OnClickListener {

    public ContentValues myValues;
    public Klausur tempklausur = new Klausur();
    public Schnitte schnittPunkte = new Schnitte();
    public Schnitte schnittNoten = new Schnitte();
    public boolean gespeichert;
    public boolean berechnet;

    static final int EDIT_INFO_ID = 0;
    static final int EDIT_SCHNITT_ID = 1;
    static final int DELETE_ID = 2;

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

        // set the context menu
        registerForContextMenu(getListView());

        // Set the klausuren ListAdapter
        List<String> klausurEntries = getKlausurenInfo();
        setListAdapter(new PopupAdapter(klausurEntries));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        // info dialog anzeigen
        showInfo(position);
    }

    @Override
    public void onClick(final View view) {
        // We need to post a Runnable to show the popup to make sure that the PopupMenu is
        // correctly positioned. The reason being that the view may change position before the
        // PopupMenu is shown.
        view.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(view);
            }
        });
    }

    public void displayKlausuren() {

        // Set the klausuren ListAdapter
        List<String> klausurEntries = getKlausurenInfo();
        setListAdapter(new PopupAdapter(klausurEntries));
    }

    /**
     * A simple array adapter that creates a list.
     */
    class PopupAdapter extends ArrayAdapter<String> {

        PopupAdapter(List<String> items) {
            super(getActivity(), R.layout.list_item, R.id.text, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {

            // Let ArrayAdapter inflate the layout and set the text
            View view = super.getView(position, convertView, container);

            // BEGIN_INCLUDE(button_popup)
            // Retrieve the popup button from the inflated view
            View popupButton = view.findViewById(R.id.button_popup);

            // Set the item as the button's tag so it can be retrieved later
            popupButton.setTag(getItem(position));

            // Set the fragment instance as the OnClickListener
            popupButton.setOnClickListener(ErgebnisseFragment.this);
            // END_INCLUDE(button_popup)

            // Finally return the view to be displayed
            return view;
        }
    }

    // BEGIN_INCLUDE(show_popup)
    private void showPopupMenu(View view) {

        final Context thisContext = getActivity();
        final PopupAdapter adapter = (PopupAdapter) getListAdapter();

        // Retrieve the clicked item from view's tag
        final String item = (String) view.getTag();

        // Create a PopupMenu, giving it the clicked view for an anchor
        PopupMenu popup = new PopupMenu(getActivity(), view);

        // Inflate our menu resource into the PopupMenu's Menu
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        // Set a listener so we are notified if a menu item is clicked
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_edit_info:
                        // edit info action
                        updateKlausur(adapter.getPosition(item));
                        return true;

                    case R.id.menu_edit_schnitt:
                        // edit schnitt action
                        updateSchnitt(adapter.getPosition(item));
                        return true;

                    case R.id.menu_delete:
                        // Remove the item from the adapter
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage(R.string.delete_message)
                                .setTitle(R.string.delete_title);

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // klausur löschen
                                MainActivity.myKlausurDelete(adapter.getPosition(item));
                                // refresh
                                adapter.remove(item);
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
                }
                return false;
            }
        });

        // Finally show the PopupMenu
        popup.show();
    }
    // END_INCLUDE(show_popup)

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
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
        View addView = myInflater.inflate(R.layout.add_klausur, null);

        final DialogWrapper editWrapper = new DialogWrapper(addView);

        String defaultKurseValue = "1ku1,1ku2,1ku3,2ku1,2ku2,2ku3";
        String defaultSemesterValue = "11/1,11/2,12/1,12/2,2016/17";

        // get spinner items from preferences
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(myContext);
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
        ArrayAdapter<String> adapterKurs = new ArrayAdapter<String>(myContext, android.R.layout.simple_spinner_item, kurs_array);
        adapterKurs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKurs.setAdapter(adapterKurs);
        spinnerKurs.setOnItemSelectedListener(new SpinnerActivity());

        final Spinner spinnerSemester = (Spinner) addView.findViewById(R.id.semester);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(myContext, android.R.layout.simple_spinner_item, semester_array);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapterSemester);
        spinnerSemester.setOnItemSelectedListener(new SpinnerActivity());


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
        EditText info = (EditText) addView.findViewById(R.id.info);

        // set edit texte
        klausur_name.setText(tempklausur.klausurName);
        info.setText(tempklausur.info);
        datumText.setText(tempklausur.datum);

        // set values in spinner via adapter
        for(int i = 0; i < adapterKurs.getCount(); i++) {
            if(tempklausur.kurs.trim().equals(adapterKurs.getItem(i))){
                spinnerKurs.setSelection(i);
                break;
            }
        }

        for(int i = 0; i < adapterSemester.getCount(); i++) {
            if(tempklausur.semester.trim().equals(adapterSemester.getItem(i))){
                spinnerSemester.setSelection(i);
                break;
            }
        }

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
            myValues.put(MyDBHelper.COLUMN_KURS, wrapper.getKurs());
            myValues.put(MyDBHelper.COLUMN_SEMESTER, wrapper.getSemester());
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

        berechnet = false;
        tempklausur = MainActivity.myListClick(position);

        final Context myContext = getActivity();
        final Activity activity = getActivity();

        LayoutInflater myInflater = LayoutInflater.from(myContext);

        if (tempklausur.mode.equals("klausur")) {

            View addView = myInflater.inflate(R.layout.fragment_punkte, null);
            final DialogWrapper punkteWrapper = new DialogWrapper(addView);

            final EditText p15 = (EditText) addView.findViewById(R.id.editP15);
            final EditText p14 = (EditText) addView.findViewById(R.id.editP14);
            final EditText p13 = (EditText) addView.findViewById(R.id.editP13);
            final EditText p12 = (EditText) addView.findViewById(R.id.editP12);
            final EditText p11 = (EditText) addView.findViewById(R.id.editP11);
            final EditText p10 = (EditText) addView.findViewById(R.id.editP10);
            final EditText p9 = (EditText) addView.findViewById(R.id.editP9);
            final EditText p8 = (EditText) addView.findViewById(R.id.editP8);
            final EditText p7 = (EditText) addView.findViewById(R.id.editP7);
            final EditText p6 = (EditText) addView.findViewById(R.id.editP6);
            final EditText p5 = (EditText) addView.findViewById(R.id.editP5);
            final EditText p4 = (EditText) addView.findViewById(R.id.editP4);
            final EditText p3 = (EditText) addView.findViewById(R.id.editP3);
            final EditText p2 = (EditText) addView.findViewById(R.id.editP2);
            final EditText p1 = (EditText) addView.findViewById(R.id.editP1);
            final EditText p0 = (EditText) addView.findViewById(R.id.editP0);

            final TextView txtPunkteErgebnis = (TextView) addView.findViewById(R.id.textErgebnisPunkte);
            Button btnPunkteBerechnen = (Button) addView.findViewById(R.id.btnBerechnenPunkte);
            Button btnPunkteReset = (Button) addView.findViewById(R.id.btnResetPunkte);

            // btn komplett speichern ausblenden
            Button btnSpeichern = (Button) addView.findViewById(R.id.btnSpeichernPunkte);
            btnSpeichern.setVisibility(View.GONE);


            // set edit texte
            if (tempklausur.p15 != 0) p15.setText(String.valueOf(tempklausur.p15));
            if (tempklausur.p14 != 0) p14.setText(String.valueOf(tempklausur.p14));
            if (tempklausur.p13 != 0) p13.setText(String.valueOf(tempklausur.p13));
            if (tempklausur.p12 != 0) p12.setText(String.valueOf(tempklausur.p12));
            if (tempklausur.p11 != 0) p11.setText(String.valueOf(tempklausur.p11));
            if (tempklausur.p10 != 0) p10.setText(String.valueOf(tempklausur.p10));
            if (tempklausur.p9 != 0) p9.setText(String.valueOf(tempklausur.p9));
            if (tempklausur.p8 != 0) p8.setText(String.valueOf(tempklausur.p8));
            if (tempklausur.p7 != 0) p7.setText(String.valueOf(tempklausur.p7));
            if (tempklausur.p6 != 0) p6.setText(String.valueOf(tempklausur.p6));
            if (tempklausur.p5 != 0) p5.setText(String.valueOf(tempklausur.p5));
            if (tempklausur.p4 != 0) p4.setText(String.valueOf(tempklausur.p4));
            if (tempklausur.p3 != 0) p3.setText(String.valueOf(tempklausur.p3));
            if (tempklausur.p2 != 0) p2.setText(String.valueOf(tempklausur.p2));
            if (tempklausur.p1 != 0) p1.setText(String.valueOf(tempklausur.p1));
            if (tempklausur.p0 != 0) p0.setText(String.valueOf(tempklausur.p0));

            // berechnen und reset
            btnPunkteBerechnen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // get noten input anzahlen
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
                        schnittPunkte = new Schnitte(anzahl, punkteschnitt, notenschnitt, ungenuegend);

                        // Ergebnis
                        txtPunkteErgebnis.setText("");
                        txtPunkteErgebnis.setText("Anzahl der Arbeiten: " + schnittPunkte.anzahl + "\n" +
                                "Punkteschnitt: " + formatErgebnis(schnittPunkte.schnittPunkte) + "\n" +
                                "Notenschnitt: " + formatErgebnis(schnittPunkte.schnittNoten) + "\n" +
                                "P3 - P0: " + formatErgebnis(schnittPunkte.prozentUngenuegend) + "%");

                        berechnet = true;

                    } else if (klausurenAnzahlBerechnen() == 0) {
                        // kein Ergebnis
                        txtPunkteErgebnis.setText("");
                        txtPunkteErgebnis.setText(R.string.errorText);

                        berechnet = false;
                    }

                    // allgemeiner reset
                    punkteInstanziieren();
                    notenInstanziieren();

                    // punkte-anzahlen zu noten-anzahlen
                    notenSynchronisieren();
                }
            });

            btnPunkteReset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // allgemeiner reset
                    punkteInstanziieren();
                    notenInstanziieren();

                    // punkte-anzahlen zu noten-anzahlen
                    notenSynchronisieren();

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
                    txtPunkteErgebnis.setText("");

                    berechnet = false;
                }
            });

            new AlertDialog.Builder(myContext)
                    .setTitle(tempklausur.klausurName + " bearbeiten")
                    .setView(addView)

                    .setPositiveButton(R.string.speichern,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    gespeichert = updatePunkteSchnittData(punkteWrapper, position, schnittPunkte);

                                    if (!gespeichert) {
                                        // toasten
                                        CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Berechnen Sie bitte erst das Ergebnis.";
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

            View addView = myInflater.inflate(R.layout.fragment_noten, null);
            final DialogWrapper notenWrapper = new DialogWrapper(addView);

            final EditText n1 = (EditText) addView.findViewById(R.id.editN1);
            final EditText n2 = (EditText) addView.findViewById(R.id.editN2);
            final EditText n3 = (EditText) addView.findViewById(R.id.editN3);
            final EditText n4 = (EditText) addView.findViewById(R.id.editN4);
            final EditText n5 = (EditText) addView.findViewById(R.id.editN5);
            final EditText n6 = (EditText) addView.findViewById(R.id.editN6);

            final TextView txtNotenErgebnis = (TextView) addView.findViewById(R.id.textErgebnisNoten);
            Button btnNotenBerechnen = (Button) addView.findViewById(R.id.btnBerechnenNoten);
            Button btnNotenReset = (Button) addView.findViewById(btnResetNoten);

            // btn komplett speichern ausblenden
            Button btnSpeichern = (Button) addView.findViewById(R.id.btnSpeichernNoten);
            btnSpeichern.setVisibility(View.GONE);

            // set edit texte
            if (tempklausur.n1 != 0) n1.setText(String.valueOf(tempklausur.n1));
            if (tempklausur.n2 != 0) n2.setText(String.valueOf(tempklausur.n2));
            if (tempklausur.n3 != 0) n3.setText(String.valueOf(tempklausur.n3));
            if (tempklausur.n4 != 0) n4.setText(String.valueOf(tempklausur.n4));
            if (tempklausur.n5 != 0) n5.setText(String.valueOf(tempklausur.n5));
            if (tempklausur.n6 != 0) n6.setText(String.valueOf(tempklausur.n6));

            // berechnen und reset
            btnNotenBerechnen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // get noten input anzahlen
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

                    // temporäre Berechnungen
                    tempBerechnungenNoten();

                    // Berechnungen
                    schulaufgabenAnzahlBerechnen();

                    if (schulaufgabenAnzahlBerechnen() != 0) {
                        notenSchnittBerechnen(tempnotensumme, anzahl);
                        ungenuegendBerechnen(tempungenuegend, anzahl);

                        // neues Objekt
                        schnittNoten = new Schnitte(anzahl, notenschnitt, ungenuegend);

                        // Ergebnis
                        txtNotenErgebnis.setText("");
                        txtNotenErgebnis.setText("Anzahl der Arbeiten: " + schnittNoten.anzahl + "\n" +
                                "Notenschnitt: " + formatErgebnis(schnittNoten.schnittNoten) + "\n" +
                                "N5 - N6: " + formatErgebnis(schnittNoten.prozentUngenuegend) + "%");

                        berechnet = true;

                    } else if (schulaufgabenAnzahlBerechnen() == 0) {
                        // kein Ergebnis
                        txtNotenErgebnis.setText("");
                        txtNotenErgebnis.setText(R.string.errorText);

                        berechnet = false;
                    }

                    // allgemeiner reset
                    punkteInstanziieren();
                    notenInstanziieren();

                    // punkte-anzahlen zu noten-anzahlen
                    notenSynchronisieren();
                }
            });

            btnNotenReset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    // allgemeiner reset
                    punkteInstanziieren();
                    notenInstanziieren();

                    // punkte-anzahlen zu noten-anzahlen
                    notenSynchronisieren();

                    // reset noten input
                    n1.setText("");
                    n2.setText("");
                    n3.setText("");
                    n4.setText("");
                    n5.setText("");
                    n6.setText("");
                    txtNotenErgebnis.setText("");

                    berechnet = false;
                }
            });

            new AlertDialog.Builder(myContext)
                    .setTitle(tempklausur.klausurName + " bearbeiten")
                    .setView(addView)

                    .setPositiveButton(R.string.speichern,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {

                                    gespeichert = updateNotenSchnittData(notenWrapper, position, schnittNoten);

                                    if (!gespeichert) {
                                        // toasten
                                        CharSequence text2 = "Die Prüfung konnte nicht gespeichert werden. Berechnen Sie bitte erst das Ergebnis.";
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

    public boolean updatePunkteSchnittData(DialogWrapper wrapper, int position, Schnitte schnittPunkte) {

        // values einlesen
        myValues = new ContentValues();

        if (!berechnet) {
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

            // Schnitt speichern
            myValues.put(MyDBHelper.COLUMN_ANZAHL, schnittPunkte.anzahl);
            myValues.put(MyDBHelper.COLUMN_SCHNITT_NOTEN, schnittPunkte.schnittNoten);
            myValues.put(MyDBHelper.COLUMN_SCHNITT_NOTEN, schnittPunkte.schnittPunkte);
            myValues.put(MyDBHelper.COLUMN_PROZENT_UNGENUEGEND, schnittPunkte.prozentUngenuegend);

            // datenbank update aktivität in MainActivity ausführen
            MainActivity.myKlausurUpdate(myValues, position);

            gespeichert = true;
        }

        return gespeichert;
    }

    public boolean updateNotenSchnittData(DialogWrapper wrapper, int position, Schnitte schnittNoten) {

        // values einlesen
        myValues = new ContentValues();

        if (!berechnet) {
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

            // Schnitt speichern
            myValues.put(MyDBHelper.COLUMN_ANZAHL, schnittNoten.anzahl);
            myValues.put(MyDBHelper.COLUMN_SCHNITT_NOTEN, schnittNoten.schnittNoten);
            myValues.put(MyDBHelper.COLUMN_PROZENT_UNGENUEGEND, schnittNoten.prozentUngenuegend);

            // datenbank update aktivität in MainActivity ausführen
            MainActivity.myKlausurUpdate(myValues, position);

            gespeichert = true;
        }

        return gespeichert;
    }
}
