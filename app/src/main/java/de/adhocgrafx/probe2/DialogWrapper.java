package de.adhocgrafx.probe2;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static de.adhocgrafx.probe2.R.id.kurs;

/**
 * Created by Hock on 22.01.2017.
 */

public class DialogWrapper {

    EditText klausurFeld = null;
    EditText infoFeld = null;
    Spinner spKurs = null;
    Spinner spSemester = null;
    TextView datumFeld = null;
    EditText kursFeld = null;
    EditText semesterFeld = null;

    View base = null;


    DialogWrapper(View base) {
        this.base = base;
    }

    public String getDatum() {
        return(getDatumFeld().getText().toString());
    }

    private TextView getDatumFeld() {
        if (datumFeld == null) {
            datumFeld = (TextView)base.findViewById(R.id.txtDate);
        }

        return(datumFeld);
    }

    public String getInfo() {
        return(getInfoFeld().getText().toString());
    }

    private EditText getInfoFeld() {
        if (infoFeld == null) {
            infoFeld = (EditText)base.findViewById(R.id.info);
        }

        return(infoFeld);
    }

    public String getKurs() {
        return(getKursFeld().getSelectedItem().toString());
    }

    private Spinner getKursFeld() {
        if (spKurs == null) {
            spKurs = (Spinner)base.findViewById(R.id.kurs);
        }

        return(spKurs);
    }

    public String getSemester() {
        return(getSemesterFeld().getSelectedItem().toString());
    }

    private Spinner getSemesterFeld() {
        if (spSemester == null) {
            spSemester = (Spinner)base.findViewById(R.id.semester);
        }

        return(spSemester);
    }

    public String getKlausur() {
        return(getKlausurFeld().getText().toString());
    }

    private EditText getKlausurFeld() {
        if (klausurFeld == null) {
            klausurFeld = (EditText)base.findViewById(R.id.klausur_name);
        }

        return(klausurFeld);
    }

    public String getKursText() {
        return(getKursTextFeld().getText().toString());
    }

    private EditText getKursTextFeld() {
        if (kursFeld == null) {
            kursFeld = (EditText)base.findViewById(R.id.kursFeld);
        }

        return(kursFeld);
    }

    public String getSemesterText() {
        return(getSemesterTextFeld().getText().toString());
    }

    private EditText getSemesterTextFeld() {
        if (semesterFeld == null) {
            semesterFeld = (EditText)base.findViewById(R.id.semesterFeld);
        }

        return(semesterFeld);
    }
}
