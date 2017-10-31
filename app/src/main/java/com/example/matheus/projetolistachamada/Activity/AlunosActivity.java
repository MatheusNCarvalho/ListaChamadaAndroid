package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.AlunoAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class AlunosActivity extends AppCompatActivity {

    private Button btVoltar;
    private ListView listView;
    private ArrayAdapter<Alunos> adapterAluno;
    private ArrayList<Alunos> alunos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        alunos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listAlunos);
        adapterAluno = new AlunoAdapter(this, alunos);
        listView.setAdapter(adapterAluno);

        firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");

        valueEventListenerAlunos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alunos.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Alunos alunosNovo = dados.getValue(Alunos.class);
                    alunos.add(alunosNovo);
                }
                adapterAluno.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        btVoltar = (Button) findViewById(R.id.btVoltarListaAlunos);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AlunosActivity.this, PrincipalActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAlunos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerAlunos);
    }
}
