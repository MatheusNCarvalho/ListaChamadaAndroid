package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.AlunoDAO;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.DAO.DisciplinaDAO;
import com.example.matheus.projetolistachamada.DAO.TurmaDAO;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CadastroAlunosActivity extends AppCompatActivity {

    private EditText etCadNome;
    private EditText etCadMatricula;
    private Button btSalvar;

    private Alunos alunos;

    private DatabaseReference firebase;

    private List<String> arrayTurma = new ArrayList<>();
    private ArrayAdapter<String> turmaAdapter;

    private List<String> arrayDisciplina = new ArrayList<>();
    private ArrayAdapter<String> disciplinaAdapter;

    private DatabaseReference database;
    private Spinner spTurmas;
    private Spinner spDisplinas;

    private TurmaDAO turmaDAO = new TurmaDAO(this);
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
    private AlunoDAO alunoDAO = new AlunoDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alunos);

        etCadNome = (EditText) findViewById(R.id.etNomeAluno);
        etCadMatricula = (EditText) findViewById(R.id.etMatriculaAluno);
        spTurmas = (Spinner) findViewById(R.id.dpTurmasAlunos);
        spDisplinas = (Spinner) findViewById(R.id.spDisciplinas);

        btSalvar = (Button) findViewById(R.id.btSalvarCadAlunos);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etCadNome.getText().toString().equals("") && !etCadMatricula.getText().toString().equals("")) {
                    alunos = new Alunos();
                    alunos.setId(UUID.randomUUID().toString());
                    alunos.setNome(etCadNome.getText().toString());
                    alunos.setMatricula(Integer.valueOf(etCadMatricula.getText().toString()));
                    alunos.setTurma(spTurmas.getSelectedItem().toString());
                    alunos.setDisciplina(spDisplinas.getSelectedItem().toString());

                    salvarAluno(alunos);
                    alunoDAO.salvar(alunos);
                    etCadNome.setText("");
                    etCadMatricula.setText("");
                    finish();

                } else {
                    Toast.makeText(CadastroAlunosActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }
            }
        });


        if (VerificaConexaoInternet.isOnline(this)) {
            turmaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayTurma);
            // Drop down layout style - list view with radio button
            turmaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spTurmas.setAdapter(turmaAdapter);

            database = FirebaseDatabase.getInstance().getReference();

            database.child("addturmas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Turmas data = snapshot.getValue(Turmas.class);
                        arrayTurma.add(data.getNome());
                    }
                    turmaAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            disciplinaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayDisciplina);
            // Drop down layout style - list view with radio button
            disciplinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spDisplinas.setAdapter(disciplinaAdapter);

            database = FirebaseDatabase.getInstance().getReference();

            database.child("adddisciplinas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Disciplinas data = snapshot.getValue(Disciplinas.class);
                        arrayDisciplina.add(data.getNome());
                    }
                    disciplinaAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {

            arrayTurma.clear();
            arrayTurma = turmaDAO.buscarTodos();

            turmaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayTurma);
            // Drop down layout style - list view with radio button
            turmaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spTurmas.setAdapter(turmaAdapter);

            arrayDisciplina.clear();
            arrayDisciplina =  disciplinaDAO.buscarTodos();

            disciplinaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayDisciplina);
            // Drop down layout style - list view with radio button
            disciplinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDisplinas.setAdapter(disciplinaAdapter);

        }



    }


    private boolean salvarAluno(Alunos alunos) {
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");
            firebase.child(alunos.getId()).setValue(alunos);
            Toast.makeText(CadastroAlunosActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_LONG).show();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
