package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.DisciplinaAdapter;
import com.example.matheus.projetolistachamada.Adapter.ProfessorAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisciplinaActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference firebase;
    private ArrayAdapter<Disciplinas> adapterDisciplina;
    private ArrayList<Disciplinas> disciplinas;
    private ValueEventListener valueEventListenerDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);
        disciplinas = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listProfessores);
        adapterDisciplina = new ArrayAdapter<Disciplinas>(DisciplinaActivity.this, android.R.layout.simple_list_item_1,disciplinas);
        listView.setAdapter(adapterDisciplina);

        firebase = ConfiguracaoFirebase.getFirebase().child("adddisciplinas");

        valueEventListenerDisciplina = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                disciplinas.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Disciplinas disciplinaNovo = dados.getValue(Disciplinas.class);
                    disciplinas.add(disciplinaNovo);
                }
                adapterDisciplina.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerDisciplina);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerDisciplina);
    }
}
