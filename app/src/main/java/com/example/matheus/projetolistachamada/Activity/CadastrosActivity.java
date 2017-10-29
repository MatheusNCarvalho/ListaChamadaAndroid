package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matheus.projetolistachamada.R;

public class CadastrosActivity extends AppCompatActivity {

    private Button btCadastroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);

        btCadastroUsuario = (Button) findViewById(R.id.btCadastroUsuario);
        btCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastrosActivity.this, CadastroAlunosActivity.class);
                startActivity(it);
            }
        });

    }
}
