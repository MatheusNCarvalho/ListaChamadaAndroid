package com.example.matheus.projetolistachamada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;

import java.util.ArrayList;

/**
 * Created by Brunno on 19/11/2017.
 */

public class DisciplinaAdapter extends ArrayAdapter<Disciplinas>{
    private ArrayList<Disciplinas> disciplinas;
    private Context context;

    public DisciplinaAdapter(@NonNull Context context, ArrayList<Disciplinas> objects) {
        super(context, 0, objects);
        this.context = context;
        this.disciplinas = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(disciplinas != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_disciplinas, parent , false);
            TextView tvDisciplina = (TextView) view.findViewById(R.id.tvDisciplinas);

            Disciplinas disciplinas2 = disciplinas.get(position);
            tvDisciplina.setText(disciplinas2.getNome());
        }
        return view;
    }
}
