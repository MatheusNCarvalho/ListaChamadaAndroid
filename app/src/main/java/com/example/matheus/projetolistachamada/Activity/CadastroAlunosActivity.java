package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DatabaseReference;

public class CadastroAlunosActivity extends AppCompatActivity {

    private EditText etCadNome;
    private EditText etCadMatricula;
    private Button btSalvar;

    private Alunos alunos;

    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_alunos);

        etCadNome = (EditText) findViewById(R.id.etNomeAluno);
        etCadMatricula = (EditText) findViewById(R.id.etMatriculaAluno);
        btSalvar = (Button) findViewById(R.id.btSalvarCadAlunos);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etCadNome.getText().toString().equals("") && !etCadMatricula.getText().toString().equals("")){
                    alunos = new Alunos();

                    alunos.setNome(etCadNome.getText().toString());
                    alunos.setMatricula(Integer.valueOf(etCadMatricula.getText().toString()));

                    salvarAluno(alunos);
                    Toast.makeText(CadastroAlunosActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_LONG).show();
                    etCadNome.setText("");
                    etCadMatricula.setText("");
                }else{
                    Toast.makeText(CadastroAlunosActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private boolean salvarAluno(Alunos alunos){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("addalunos");
            firebase.child(alunos.getNome()).setValue(alunos);
            Toast.makeText(CadastroAlunosActivity.this, " Aluno inserido com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
