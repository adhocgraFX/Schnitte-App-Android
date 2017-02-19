package de.adhocgrafx.probe2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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
import static de.adhocgrafx.probe2.MyDBHelper.COLUMN_KLAUSURNAME;
import static de.adhocgrafx.probe2.R.id.txtDate;

public class ErgebnisseFragment extends ListFragment {

    public ContentValues myValues;
    public Klausur tempklausur = new Klausur();
    public boolean gespeichert;


    static final int INFO_ID = 0;
    static final int EDIT_ID = 1;
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

        registerForContextMenu(getListView());

        displayKlausuren();
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

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
        menu.add(0, INFO_ID, 0, "Informationen");
        menu.add(0, EDIT_ID, 0, "Bearbeiten");
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

            case EDIT_ID:
                updateKlausur((int) info.id);
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
            txtInfo2.setText(formatAnzahl(tempklausur.anzahl) + "\n" + formatErgebnis(tempklausur.schnittPunkte) + "\n" +
                    formatErgebnis(tempklausur.schnittNoten) + "\n" + formatErgebnis(tempklausur.prozentUngenuegend) + "%");
        }
        else if (tempklausur.mode.equals("schulaufgabe")) {
            lblInfo1.setText("Klasse:" + "\n" + "Schuljahr:" + "\n" + "Datum:");
            lblInfo2.setText("Anzahl:" + "\n" + "Notenschnitt:" + "\n" + "N5 - N6:");
            txtInfo2.setText(formatAnzahl(tempklausur.anzahl) + "\n" + formatErgebnis(tempklausur.schnittNoten) + "\n" +
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

                                gespeichert = updateKlausur(editWrapper, position);

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

    public boolean updateKlausur(DialogWrapper wrapper, int position) {

        // values einlesen
        myValues = new ContentValues();

        if (wrapper.getKlausur().isEmpty()) {
            // nothing is saved
            gespeichert = false;

        } else {
            // endgültig speichern
            myValues.put(COLUMN_KLAUSURNAME, wrapper.getKlausur());
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

    // delet dialog

}
