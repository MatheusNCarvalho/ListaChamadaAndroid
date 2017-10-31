package com.example.matheus.projetolistachamada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.R;

import java.util.ArrayList;

/**
 * Created by Brunno on 30/10/17.
 */

public class AlunoAdapter extends ArrayAdapter<Alunos>{

    private ArrayList<Alunos> aluno;
    private Context context;
    public AlunoAdapter(@NonNull Context context, ArrayList<Alunos> objects) {
        super(context, 0,objects);
        this.context = context;
        this.aluno = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(aluno != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_alunos, parent , false);
            TextView tvNome = (TextView) view.findViewById(R.id.tvNomeAluno);
            TextView tvMatricula = (TextView) view.findViewById(R.id.tvMatriculaAluno);

            Alunos alunos2 = aluno.get(position);
            tvNome.setText(alunos2.getNome());
            tvMatricula.setText(alunos2.getMatricula().toString());
        }

        return view;
    }
}
