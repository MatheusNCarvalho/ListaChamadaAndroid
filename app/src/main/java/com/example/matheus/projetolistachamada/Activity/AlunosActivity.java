package com.example.matheus.projetolistachamada.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.AlunoAdapter;
import com.example.matheus.projetolistachamada.Adapter.ChamadaALunoAdapter;
import com.example.matheus.projetolistachamada.DAO.AlunoDAO;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class AlunosActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter<Alunos> adapterAluno;
    private ArrayList<Alunos> alunos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerAlunos;
    private Toolbar tbAlunos;

    private Alunos alunoSelecionado;

    private AlunoDAO alunoDAO = new AlunoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        alunos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listAlunos);
        tbAlunos =  (Toolbar) findViewById(R.id.tbAluno);
        tbAlunos.setTitle("Listas de Alunos");
        tbAlunos.setTitleTextColor(Color.WHITE);


        setSupportActionBar(tbAlunos);

        firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");


        if (VerificaConexaoInternet.isOnline(this)) {
            valueEventListenerAlunos = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    alunos.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Alunos alunosNovo = dados.getValue(Alunos.class);
                        alunos.add(alunosNovo);
                    }


                    adapterAluno = new ArrayAdapter<Alunos>(AlunosActivity.this, android.R.layout.simple_list_item_1,alunos);
                    listView.setAdapter(adapterAluno);
                    adapterAluno.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            Log.d("Conectado", "Conectado");
        } else {

            alunos.clear();
            alunos = alunoDAO.buscarTodos();

            adapterAluno = new ArrayAdapter<Alunos>(AlunosActivity.this, android.R.layout.simple_list_item_1, alunos);
            listView.setAdapter(adapterAluno);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (VerificaConexaoInternet.isOnline(this))
            firebase.addValueEventListener(valueEventListenerAlunos);


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (VerificaConexaoInternet.isOnline(this))
            firebase.removeEventListener(valueEventListenerAlunos);
    }


//    public static boolean isOnline(Context contexto) {
//        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);// Pego a conectividade do contexto
//
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();// Crio o objeto netInfo que recebe as informacoes da Network
//
//        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) { // Se nao tem conectividade retorna false
//            return true;
//        }
//        return false;
//    }


}
