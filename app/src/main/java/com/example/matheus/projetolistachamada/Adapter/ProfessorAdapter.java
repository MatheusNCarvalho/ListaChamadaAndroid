package com.example.matheus.projetolistachamada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.R;

import java.util.ArrayList;

/**
 * Created by Brunno on 31/10/17.
 */

public class ProfessorAdapter extends ArrayAdapter<Professores> {
    private ArrayList<Professores> professor;
    private Context context;

    public ProfessorAdapter(@NonNull Context context, ArrayList<Professores> objetcs) {
        super(context, 0,objetcs);
        this.context = context;
        this.professor = objetcs;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View view = null;

        if(professor != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_alunos, parent , false);
            TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
            TextView tvMatricula = (TextView) view.findViewById(R.id.tvMatricula);

            Professores professor2 = professor.get(position);
            tvNome.setText(professor2.getNome());
            tvMatricula.setText(professor2.getMatricula().toString());
        }
        return view;
    }
}
