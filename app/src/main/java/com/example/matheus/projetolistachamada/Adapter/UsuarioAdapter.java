package com.example.matheus.projetolistachamada.Adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.R;

import java.util.ArrayList;

/**
 * Created by Brunno on 02/11/17.
 */

public class UsuarioAdapter extends ArrayAdapter<Usuarios> {

    private Context context;
    private ArrayList<Usuarios> usuarios;
    public UsuarioAdapter( Context context, ArrayList<Usuarios> objects) {
        super(context, 0, objects);
        this.context = context;
        this.usuarios = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(usuarios != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_alunos, parent , false);
            TextView tvNome = (TextView) view.findViewById(R.id.tvNome);
            TextView tvEmail = (TextView) view.findViewById(R.id.tvMatricula);

            Usuarios usuarios2 = usuarios.get(position);
            tvNome.setText(usuarios2.getNome());
            tvEmail.setText(usuarios2.getEmail().toString());
        }
        return view;
    }
}
