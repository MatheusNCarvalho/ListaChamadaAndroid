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
import com.example.matheus.projetolistachamada.DAO.ProfessorDAO;
import com.example.matheus.projetolistachamada.DAO.TurmaDAO;
import com.example.matheus.projetolistachamada.Entidades.Alunos;

import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;

import com.google.firebase.auth.FirebaseAuth;
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
    private boolean condicaoProfessor = false;
    private TextView textView;
    private DatabaseReference databaseReference;

    private FirebaseAuth autenticacao;


    private List<Alunos> alunosArrayList = new ArrayList<Alunos>();
    private List<Turmas> turmasArrayList = new ArrayList<Turmas>();
    private List<Professores> professoresArrayList = new ArrayList<Professores>();


    private AlunoDAO alunoDAO = new AlunoDAO(this);
    private TurmaDAO turmaDAO = new TurmaDAO(this);
    private ProfessorDAO professorDAO = new ProfessorDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregamento);

        autenticacao = ConfiguracaoFirebase.getAutenticacao();


        if (VerificaConexaoInternet.isOnline(this) && autenticacao.getCurrentUser() !=null) {
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


    public void carregarSincronizamento() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        carregaListaUsuario();
        carregaListaTurma();
        carregaListaProfessores();


    }


    public void paraSincronizamento() {

        progressBar = (ProgressBar) findViewById(R.id.progressBarSincroniza);

        if (condicaoAluno && condicaoTurma && condicaoProfessor) {
            progressBar.setVisibility(View.INVISIBLE);

            Intent carr = new Intent(CarregamentoActivity.this, Loginctivity.class);
            startActivity(carr);
        }


    }


    private void carregaListaProfessores() {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("addprofessores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Professores obj = snapshot.getValue(Professores.class);
                    professoresArrayList.add(obj);
                }

                if (professoresArrayList.size() > 0) {
                    professorDAO.delete();
                    professorDAO.save(professoresArrayList);
                    professoresArrayList.clear();
                    condicaoProfessor = true;
                    paraSincronizamento();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
