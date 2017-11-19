package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.DAO.AlunoDAO;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;

import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CarregamentoActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private boolean condicao = false;
    private TextView textView;
    private DatabaseReference databaseReference;


    private List<Alunos> alunosArrayList = new ArrayList<Alunos>();

    private AlunoDAO alunoDAO = new AlunoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregamento);


        if (VerificaConexaoInternet.isOnline(this)) {
            progressBar = (ProgressBar) findViewById(R.id.progressBarSincroniza);
            textView = (TextView) findViewById(R.id.textViewTexto);
            //progess();
            textView.setText("Fazendo a Sincronização da base de dados");

            carregarSincronizamento();

        } else {
            Intent intent = new Intent(CarregamentoActivity.this, Loginctivity.class);
            startActivity(intent);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

//    public void progess() {
//        new Thread(new Runnable() {
//            public void run() {
//                while (progressoStatus <5) {
//                    progressoStatus +=  1;
//
//                    handler.post(new Runnable() {
//                        public void run() {
//                            carregaListaUsuario();
//                            progressBar.setProgress(progressoStatus);
//                            textView.setText("Fazendo a Sincronização da base de dados");
//
//                        }
//                    });
//                    try {
//                        // Sleep for 200 milliseconds.
//                        Thread.sleep(200);
//
////                        carregarSincronizamento();
////                        progressBar.setProgress(progressoStatus);
////                        textView.setText("Fazendo a Sincronização da base de dados");
//
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        }).start();
//        Intent intent = new Intent(CarregamentoActivity.this, Loginctivity.class);
//        startActivity(intent);
//    }

    public void carregarSincronizamento() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        carregaListaUsuario();


    }

    public void paraSincronizamento() {

        progressBar = (ProgressBar) findViewById(R.id.progressBarSincroniza);

        if (condicao) {
            progressBar.setVisibility(View.INVISIBLE);

            Intent carr = new Intent(CarregamentoActivity.this, Loginctivity.class);
            startActivity(carr);
        }


    }

    public void carregaListaUsuario() {

        databaseReference = ConfiguracaoFirebase.getFirebase().child("addalunos");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alunosArrayList.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Alunos alunosNovo = dados.getValue(Alunos.class);
                    alunosArrayList.add(alunosNovo);
                }

                if (alunosArrayList.size() > 0) {

                    alunoDAO = new AlunoDAO(CarregamentoActivity.this);
                    alunoDAO.delete();
                    alunoDAO.save(alunosArrayList);
                    alunosArrayList.clear();

                    condicao = true;
                    paraSincronizamento();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
