package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matheus.projetolistachamada.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button btCadastros;
    private Button btAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btCadastros = (Button) findViewById(R.id.btCadastros);
        btCadastros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PrincipalActivity.this, CadastrosActivity.class);
                startActivity(it);
            }
        });

        btAlunos = (Button) findViewById(R.id.btlistaAlunos);
        btAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PrincipalActivity.this, AlunosActivity.class);
                startActivity(it);
            }
        });

    }
}
