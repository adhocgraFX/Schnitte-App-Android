package de.adhocgrafx.probe2;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * Created by Hock on 22.01.2017.
 */

public class DialogWrapper {

    View base = null;

    EditText klausurFeld = null;
    EditText infoFeld = null;

    Spinner spKurs = null;
    Spinner spSemester = null;
    EditText kursFeld = null;
    EditText semesterFeld = null;

    TextView datumFeld = null;

    EditText N1 = null;
    EditText N2 = null;
    EditText N3 = null;
    EditText N4 = null;
    EditText N5 = null;
    EditText N6 = null;

    EditText P15 = null;
    EditText P14 = null;
    EditText P13 = null;
    EditText P12 = null;
    EditText P11 = null;
    EditText P10 = null;
    EditText P9 = null;
    EditText P8 = null;
    EditText P7 = null;
    EditText P6 = null;
    EditText P5 = null;
    EditText P4 = null;
    EditText P3 = null;
    EditText P2 = null;
    EditText P1 = null;
    EditText P0 = null;

    DialogWrapper(View base) {
        this.base = base;
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

    public String getInfo() {
        return(getInfoFeld().getText().toString());
    }
    private EditText getInfoFeld() {
        if (infoFeld == null) {
            infoFeld = (EditText)base.findViewById(R.id.info);
        }

        return(infoFeld);
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

    public String getN1() {
        return(getN1Feld().getText().toString());
    }
    private EditText getN1Feld() {
        if (N1 == null) {
            N1 = (EditText)base.findViewById(R.id.editN1);
        }

        return(N1);
    }

    public String getN2() {
        return(getN2Feld().getText().toString());
    }
    private EditText getN2Feld() {
        if (N2 == null) {
            N2 = (EditText)base.findViewById(R.id.editN2);
        }

        return(N2);
    }

    public String getN3() {
        return(getN3Feld().getText().toString());
    }
    private EditText getN3Feld() {
        if (N3 == null) {
            N3 = (EditText)base.findViewById(R.id.editN3);
        }

        return(N3);
    }

    public String getN4() {
        return(getN4Feld().getText().toString());
    }
    private EditText getN4Feld() {
        if (N4 == null) {
            N4 = (EditText)base.findViewById(R.id.editN4);
        }

        return(N4);
    }

    public String getN5() {
        return(getN5Feld().getText().toString());
    }
    private EditText getN5Feld() {
        if (N5 == null) {
            N5 = (EditText)base.findViewById(R.id.editN5);
        }

        return(N5);
    }

    public String getN6() {
        return(getN6Feld().getText().toString());
    }
    private EditText getN6Feld() {
        if (N6 == null) {
            N6 = (EditText)base.findViewById(R.id.editN6);
        }

        return(N6);
    }

    public String getP15() {
        return(getP15Feld().getText().toString());
    }
    private EditText getP15Feld() {
        if (P15 == null) {
            P15 = (EditText)base.findViewById(R.id.editP15);
        }

        return(P15);
    }

    public String getP14() {
        return(getP14Feld().getText().toString());
    }
    private EditText getP14Feld() {
        if (P14 == null) {
            P14 = (EditText)base.findViewById(R.id.editP14);
        }

        return(P14);
    }

    public String getP13() {
        return(getP13Feld().getText().toString());
    }
    private EditText getP13Feld() {
        if (P13 == null) {
            P13 = (EditText)base.findViewById(R.id.editP13);
        }

        return(P13);
    }

    public String getP12() {
        return(getP12Feld().getText().toString());
    }
    private EditText getP12Feld() {
        if (P12 == null) {
            P12 = (EditText)base.findViewById(R.id.editP12);
        }

        return(P12);
    }

    public String getP11() {
        return(getP11Feld().getText().toString());
    }
    private EditText getP11Feld() {
        if (P11 == null) {
            P11 = (EditText)base.findViewById(R.id.editP11);
        }

        return(P11);
    }

    public String getP10() {
        return(getP10Feld().getText().toString());
    }
    private EditText getP10Feld() {
        if (P10 == null) {
            P10 = (EditText)base.findViewById(R.id.editP10);
        }

        return(P10);
    }

    public String getP9() {
        return(getP9Feld().getText().toString());
    }
    private EditText getP9Feld() {
        if (P9 == null) {
            P9 = (EditText)base.findViewById(R.id.editP9);
        }

        return(P9);
    }

    public String getP8() {
        return(getP8Feld().getText().toString());
    }
    private EditText getP8Feld() {
        if (P8 == null) {
            P8 = (EditText)base.findViewById(R.id.editP8);
        }

        return(P8);
    }

    public String getP7() {
        return(getP7Feld().getText().toString());
    }
    private EditText getP7Feld() {
        if (P7 == null) {
            P7 = (EditText)base.findViewById(R.id.editP7);
        }

        return(P7);
    }

    public String getP6() {
        return(getP6Feld().getText().toString());
    }
    private EditText getP6Feld() {
        if (P6 == null) {
            P6 = (EditText)base.findViewById(R.id.editP6);
        }

        return(P6);
    }

    public String getP5() {
        return(getP5Feld().getText().toString());
    }
    private EditText getP5Feld() {
        if (P5 == null) {
            P5 = (EditText)base.findViewById(R.id.editP5);
        }

        return(P5);
    }

    public String getP4() {
        return(getP4Feld().getText().toString());
    }
    private EditText getP4Feld() {
        if (P4 == null) {
            P4 = (EditText)base.findViewById(R.id.editP4);
        }

        return(P4);
    }

    public String getP3() {
        return(getP3Feld().getText().toString());
    }
    private EditText getP3Feld() {
        if (P3 == null) {
            P3 = (EditText)base.findViewById(R.id.editP3);
        }

        return(P3);
    }

    public String getP2() {
        return(getP2Feld().getText().toString());
    }
    private EditText getP2Feld() {
        if (P2 == null) {
            P2 = (EditText)base.findViewById(R.id.editP2);
        }

        return(P2);
    }

    public String getP1() {
        return(getP1Feld().getText().toString());
    }
    private EditText getP1Feld() {
        if (P1 == null) {
            P1 = (EditText)base.findViewById(R.id.editP1);
        }

        return(P1);
    }

    public String getP0() {
        return(getP0Feld().getText().toString());
    }
    private EditText getP0Feld() {
        if (P0 == null) {
            P0 = (EditText)base.findViewById(R.id.editP0);
        }

        return(P0);
    }
}
