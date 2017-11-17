package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.Adapter.AlunoAdapter;
import com.example.matheus.projetolistachamada.Adapter.ChamadaALunoAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Chamadas;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ListaChamadaActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Alunos> adapterAlunoChamada;
    private ArrayList<Alunos> alunos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerAlunos;

    private Alunos alunosChamada;

    private Chamadas chamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chamada);
        alunos = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listChamada);
        registerForContextMenu(listView);

        adapterAlunoChamada = new ChamadaALunoAdapter(this, alunos);
        listView.setAdapter(adapterAlunoChamada);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alunosChamada = adapterAlunoChamada.getItem(position);
                return false;
            }
        });

        firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");

        valueEventListenerAlunos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alunos.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Alunos alunosNovo = dados.getValue(Alunos.class);
                    alunos.add(alunosNovo);
                }
                adapterAlunoChamada.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item01 = menu.add(" 2 faltas");
        item01.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                salvarFaltasChamada("2");

                return false;
            }
        });
        MenuItem item02 = menu.add(" 4 faltas");
        item02.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                salvarFaltasChamada("4");

                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    private boolean salvarFaltasChamada( String faltas){
        try{
            chamadas = new Chamadas();
            chamadas.setNomeAluno(alunosChamada.getNome());
            chamadas.setTurma(alunosChamada.getTurma());

            Date d = new Date();
            chamadas.setDataChamada(d.getTime());
            chamadas.setFaltas(faltas);

            firebase = ConfiguracaoFirebase.getFirebase().child("addchamadas");
            firebase.child(chamadas.getNomeAluno()).setValue(alunos);
            Toast.makeText(ListaChamadaActivity.this, "falta salva com sucesso!", Toast.LENGTH_LONG).show();


            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
