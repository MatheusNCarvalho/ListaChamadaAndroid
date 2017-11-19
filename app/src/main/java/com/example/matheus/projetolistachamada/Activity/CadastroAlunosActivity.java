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

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.DAO.TurmaDAO;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
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

public class CadastroAlunosActivity extends AppCompatActivity {

    private EditText etCadNome;
    private EditText etCadMatricula;
    private Button btSalvar;

    private Alunos alunos;



    private DatabaseReference firebase;

    private ArrayList<String> arrayTurmaString = new ArrayList<>();
    private ArrayAdapter<String> turmaAdapter;
    private DatabaseReference database;
    private Spinner spTurmas;

    private TurmaDAO turmaDAO =  new TurmaDAO(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alunos);

        etCadNome = (EditText) findViewById(R.id.etNomeAluno);
        etCadMatricula = (EditText) findViewById(R.id.etMatriculaAluno);
        spTurmas = (Spinner) findViewById(R.id.dpTurmasAlunos);

        btSalvar = (Button) findViewById(R.id.btSalvarCadAlunos);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etCadNome.getText().toString().equals("") && !etCadMatricula.getText().toString().equals("")) {
                    alunos = new Alunos();


                    alunos.setNome(etCadNome.getText().toString());
                    alunos.setMatricula(Integer.valueOf(etCadMatricula.getText().toString()));
                    alunos.setTurma(spTurmas.getSelectedItem().toString());

                    salvarAluno(alunos);
                    etCadNome.setText("");
                    etCadMatricula.setText("");


                } else {
                    Toast.makeText(CadastroAlunosActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (VerificaConexaoInternet.isOnline(this)) {
            turmaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayTurmaString);
            // Drop down layout style - list view with radio button
            turmaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spTurmas.setAdapter(turmaAdapter);

            database = FirebaseDatabase.getInstance().getReference();

            database.child("addturmas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Turmas data = snapshot.getValue(Turmas.class);
                        arrayTurmaString.add(data.getNome());
                    }
                    turmaAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {

            arrayTurmaString.clear();
            arrayTurmaString = turmaDAO.buscarTodos();

            turmaAdapter = new ArrayAdapter<String>(CadastroAlunosActivity.this, android.R.layout.simple_spinner_item, arrayTurmaString);
            // Drop down layout style - list view with radio button
            turmaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spTurmas.setAdapter(turmaAdapter);
        }


    }

    private boolean salvarAluno(Alunos alunos) {
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");
            firebase.child(alunos.getNome()).setValue(alunos);
            Toast.makeText(CadastroAlunosActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_LONG).show();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
