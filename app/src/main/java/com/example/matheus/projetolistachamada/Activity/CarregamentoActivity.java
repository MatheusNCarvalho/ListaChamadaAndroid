package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.matheus.projetolistachamada.DAO.AlunoDAO;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.DAO.TurmaDAO;
import com.example.matheus.projetolistachamada.Entidades.Alunos;

import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CarregamentoActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private boolean condicaoAluno = false;
    private boolean condicaoTurma = false;
    private TextView textView;
    private DatabaseReference databaseReference;


    private List<Alunos> alunosArrayList = new ArrayList<Alunos>();
    private List<Turmas> turmasArrayList = new ArrayList<Turmas>();


    private AlunoDAO alunoDAO = new AlunoDAO(this);
    private TurmaDAO turmaDAO = new TurmaDAO(this);

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

        Log.i("Scri", "Teste Start");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Scri", "Teste Start");
    }


    public void carregarSincronizamento() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        carregaListaUsuario();
        carregaListaTurma();


    }


    public void paraSincronizamento() {

        progressBar = (ProgressBar) findViewById(R.id.progressBarSincroniza);

        if (condicaoAluno && condicaoTurma) {
            progressBar.setVisibility(View.INVISIBLE);

            Intent carr = new Intent(CarregamentoActivity.this, Loginctivity.class);
            startActivity(carr);
        }


    }

    private void carregaListaTurma() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("addturmas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Turmas data = snapshot.getValue(Turmas.class);
                    turmasArrayList.add(data);
                }

                if (turmasArrayList.size() > 0) {
                    turmaDAO.delete();
                    turmaDAO.save(turmasArrayList);
                    turmasArrayList.clear();
                    condicaoTurma = true;
                    paraSincronizamento();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                    condicaoAluno = true;
                    paraSincronizamento();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Scrpit", "net" + databaseError);

            }
        });

    }


}
