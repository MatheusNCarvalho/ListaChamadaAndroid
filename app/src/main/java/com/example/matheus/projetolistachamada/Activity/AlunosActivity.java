package com.example.matheus.projetolistachamada.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.AlunoAdapter;
import com.example.matheus.projetolistachamada.Adapter.ChamadaALunoAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


        if (isOnline(this)) {
            valueEventListenerAlunos = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    alunos.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Alunos alunosNovo = dados.getValue(Alunos.class);
                        alunos.add(alunosNovo);
                    }
                    adapterAluno.notifyDataSetChanged();

                    Log.d("Conectado", "conectado");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            Log.d("Conectado", "Conectado");
        } else {
            //firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");
            DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("addalunos");

            scoresRef.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // System.out.println("The " + dataSnapshot.getKey() + " dinosaur's score is " + dataSnapshot.getValue());

                    alunos.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Alunos alunosNovo = dados.getValue(Alunos.class);
                        alunos.add(alunosNovo);
                    }
                    adapterAluno.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Log.d("Nao Conectado", "Nao Conectado");
        }


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
        Log.d("asda", alunos.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerAlunos);
    }

    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        System.out.println("conectivtyManager"+conectivtyManager.getActiveNetworkInfo().toString());

        System.out.println("getActiveNetworkInfo: "+conectivtyManager.getActiveNetworkInfo().isAvailable());

        System.out.println("getActiveNetworkInfo isConnec: "+conectivtyManager.getActiveNetworkInfo().isConnected());

        if (conectivtyManager.getActiveNetworkInfo() != null  && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected())
        {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }


    public static boolean isOnline(Context contexto) {
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);// Pego a conectividade do contexto

        NetworkInfo netInfo = cm.getActiveNetworkInfo();// Crio o objeto netInfo que recebe as informacoes da Network

        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) { // Se nao tem conectividade retorna false
            return true;
        }
        return false;
    }



}
