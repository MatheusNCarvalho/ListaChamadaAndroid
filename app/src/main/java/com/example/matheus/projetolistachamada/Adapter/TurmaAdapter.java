package com.example.matheus.projetolistachamada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;

import java.util.ArrayList;

/**
 * Created by Brunno on 31/10/17.
 */

public class TurmaAdapter extends ArrayAdapter<Turmas> {
    private ArrayList<Turmas> turma;
    private Context context;
    public TurmaAdapter( Context context, ArrayList<Turmas> objetcs) {
        super(context, 0, objetcs);
        this.context = context;
        this.turma = objetcs;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(turma != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_alunos, parent , false);
            TextView tvNome = (TextView) view.findViewById(R.id.tvNome);

            Turmas turma2 = turma.get(position);
            tvNome.setText(turma2.getNome());

        }
        return view;
    }
}
