package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.auth.FirebaseAuth;

public class CadastrosActivity extends AppCompatActivity {

    private Button btCadastroAluno;
    private Button btCadastroProfessor;
    private Button btCadastroTurma;
    private Button btCadastroUsuario;
    private Button btnLogout;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);

        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        btCadastroAluno = (Button) findViewById(R.id.btCadastroAluno);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btCadastroAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastrosActivity.this, AlunosActivity.class);
                startActivity(it);
            }
        });

        btCadastroProfessor = (Button) findViewById(R.id.btCadastroProfessor);
        btCadastroProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastrosActivity.this, CadastroProfessoresActivity.class);
                startActivity(it);
            }
        });

        btCadastroTurma = (Button) findViewById(R.id.btCadastroTurma);
        btCadastroTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastrosActivity.this, CadastroTurmasActivity.class);
                startActivity(it);
            }
        });

        btCadastroUsuario = (Button) findViewById(R.id.btCadastroUsuario);
        btCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                startActivity(it);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao.signOut();

                Intent intent = new Intent(CadastrosActivity.this, Loginctivity.class);
                startActivity(intent);
            }
        });

    }
}
