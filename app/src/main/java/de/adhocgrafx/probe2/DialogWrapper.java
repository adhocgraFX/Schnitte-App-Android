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

    TextView datumFeld = null;

    EditText n1 = null;
    EditText n2 = null;
    EditText n3 = null;
    EditText n4 = null;
    EditText n5 = null;
    EditText n6 = null;

    EditText p15 = null;
    EditText p14 = null;
    EditText p13 = null;
    EditText p12 = null;
    EditText p11 = null;
    EditText p10 = null;
    EditText p9 = null;
    EditText p8 = null;
    EditText p7 = null;
    EditText p6 = null;
    EditText p5 = null;
    EditText p4 = null;
    EditText p3 = null;
    EditText p2 = null;
    EditText p1 = null;
    EditText p0 = null;

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

    public String getN1() {
        return(getN1Feld().getText().toString());
    }
    private EditText getN1Feld() {
        if (n1 == null) {
            n1 = (EditText)base.findViewById(R.id.editN1);
        }

        return(n1);
    }

    public String getN2() {
        return(getN2Feld().getText().toString());
    }
    private EditText getN2Feld() {
        if (n2 == null) {
            n2 = (EditText)base.findViewById(R.id.editN2);
        }

        return(n2);
    }

    public String getN3() {
        return(getN3Feld().getText().toString());
    }
    private EditText getN3Feld() {
        if (n3 == null) {
            n3 = (EditText)base.findViewById(R.id.editN3);
        }

        return(n3);
    }

    public String getN4() {
        return(getN4Feld().getText().toString());
    }
    private EditText getN4Feld() {
        if (n4 == null) {
            n4 = (EditText)base.findViewById(R.id.editN4);
        }

        return(n4);
    }

    public String getN5() {
        return(getN5Feld().getText().toString());
    }
    private EditText getN5Feld() {
        if (n5 == null) {
            n5 = (EditText)base.findViewById(R.id.editN5);
        }

        return(n5);
    }

    public String getN6() {
        return(getN6Feld().getText().toString());
    }
    private EditText getN6Feld() {
        if (n6 == null) {
            n6 = (EditText)base.findViewById(R.id.editN6);
        }

        return(n6);
    }

    public String getP15() {
        return(getP15Feld().getText().toString());
    }
    private EditText getP15Feld() {
        if (p15 == null) {
            p15 = (EditText)base.findViewById(R.id.editP15);
        }

        return(p15);
    }

    public String getP14() {
        return(getP14Feld().getText().toString());
    }
    private EditText getP14Feld() {
        if (p14 == null) {
            p14 = (EditText)base.findViewById(R.id.editP14);
        }

        return(p14);
    }

    public String getP13() {
        return(getP13Feld().getText().toString());
    }
    private EditText getP13Feld() {
        if (p13 == null) {
            p13 = (EditText)base.findViewById(R.id.editP13);
        }

        return(p13);
    }

    public String getP12() {
        return(getP12Feld().getText().toString());
    }
    private EditText getP12Feld() {
        if (p12 == null) {
            p12 = (EditText)base.findViewById(R.id.editP12);
        }

        return(p12);
    }

    public String getP11() {
        return(getP11Feld().getText().toString());
    }
    private EditText getP11Feld() {
        if (p11 == null) {
            p11 = (EditText)base.findViewById(R.id.editP11);
        }

        return(p11);
    }

    public String getP10() {
        return(getP10Feld().getText().toString());
    }
    private EditText getP10Feld() {
        if (p10 == null) {
            p10 = (EditText)base.findViewById(R.id.editP10);
        }

        return(p10);
    }

    public String getP9() {
        return(getP9Feld().getText().toString());
    }
    private EditText getP9Feld() {
        if (p9 == null) {
            p9 = (EditText)base.findViewById(R.id.editP9);
        }

        return(p9);
    }

    public String getP8() {
        return(getP8Feld().getText().toString());
    }
    private EditText getP8Feld() {
        if (p8 == null) {
            p8 = (EditText)base.findViewById(R.id.editP8);
        }

        return(p8);
    }

    public String getP7() {
        return(getP7Feld().getText().toString());
    }
    private EditText getP7Feld() {
        if (p7 == null) {
            p7 = (EditText)base.findViewById(R.id.editP7);
        }

        return(p7);
    }

    public String getP6() {
        return(getP6Feld().getText().toString());
    }
    private EditText getP6Feld() {
        if (p6 == null) {
            p6 = (EditText)base.findViewById(R.id.editP6);
        }

        return(p6);
    }

    public String getP5() {
        return(getP5Feld().getText().toString());
    }
    private EditText getP5Feld() {
        if (p5 == null) {
            p5 = (EditText)base.findViewById(R.id.editP5);
        }

        return(p5);
    }

    public String getP4() {
        return(getP4Feld().getText().toString());
    }
    private EditText getP4Feld() {
        if (p4 == null) {
            p4 = (EditText)base.findViewById(R.id.editP4);
        }

        return(p4);
    }

    public String getP3() {
        return(getP3Feld().getText().toString());
    }
    private EditText getP3Feld() {
        if (p3 == null) {
            p3 = (EditText)base.findViewById(R.id.editP3);
        }

        return(p3);
    }

    public String getP2() {
        return(getP2Feld().getText().toString());
    }
    private EditText getP2Feld() {
        if (p2 == null) {
            p2 = (EditText)base.findViewById(R.id.editP2);
        }

        return(p2);
    }

    public String getP1() {
        return(getP1Feld().getText().toString());
    }
    private EditText getP1Feld() {
        if (p1 == null) {
            p1 = (EditText)base.findViewById(R.id.editP1);
        }

        return(p1);
    }

    public String getP0() {
        return(getP0Feld().getText().toString());
    }
    private EditText getP0Feld() {
        if (p0 == null) {
            p0 = (EditText)base.findViewById(R.id.editP0);
        }

        return(p0);
    }
}
