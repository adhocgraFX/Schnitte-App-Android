package de.adhocgrafx.probe2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static de.adhocgrafx.probe2.Berechnungen.notenInstanziieren;
import static de.adhocgrafx.probe2.Berechnungen.notenSynchronisieren;
import static de.adhocgrafx.probe2.Berechnungen.punkteInstanziieren;
import static de.adhocgrafx.probe2.R.id.textErgebnisNoten;

public class NotenFragment extends Fragment {

    public NotenFragment() {
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
        return inflater.inflate(R.layout.fragment_noten, container, false);
    }
}
