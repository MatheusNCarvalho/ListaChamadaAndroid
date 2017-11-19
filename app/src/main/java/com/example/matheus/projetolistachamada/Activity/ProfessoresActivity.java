package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.ProfessorAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.DAO.ProfessorDAO;
import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfessoresActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference firebase;
    private ArrayAdapter<Professores> adapterProfessor;
    private ArrayList<Professores> professores;
    private ValueEventListener valueEventListenerProfessor;

    private ProfessorDAO professorDAO =  new ProfessorDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professores);
        professores = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listProfessores);


        if (VerificaConexaoInternet.isOnline(this)) {
            firebase = ConfiguracaoFirebase.getFirebase().child("addprofessores");

            valueEventListenerProfessor = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    professores.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Professores professorNovo = dados.getValue(Professores.class);
                        professores.add(professorNovo);
                    }

                    adapterProfessor = new ProfessorAdapter(ProfessoresActivity.this, professores);
                    listView.setAdapter(adapterProfessor);

                    adapterProfessor.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };


        } else {
            professores.clear();
            professores = professorDAO.buscarTodos();
            adapterProfessor =  new ArrayAdapter<Professores>(ProfessoresActivity.this, android.R.layout.simple_list_item_1, professores);
            listView.setAdapter(adapterProfessor);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (VerificaConexaoInternet.isOnline(this))
            firebase.addValueEventListener(valueEventListenerProfessor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (VerificaConexaoInternet.isOnline(this))
            firebase.removeEventListener(valueEventListenerProfessor);
    }
}
