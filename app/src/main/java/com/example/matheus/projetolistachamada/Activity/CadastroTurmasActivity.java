package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DatabaseReference;

public class CadastroTurmasActivity extends AppCompatActivity {

    private EditText etNomeTurma;
    private Button btSalvar;
    private Turmas turmas;

    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turmas);

        etNomeTurma = (EditText) findViewById(R.id.etNomeTurma);
        btSalvar = (Button) findViewById(R.id.btSalvarCadTurmas);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNomeTurma.getText().toString().equals("")){
                    turmas = new Turmas();
                    turmas.setNome(etNomeTurma.getText().toString());

                    salvarTurma(turmas);
                    etNomeTurma.setText("");
                }else{
                    Toast.makeText(CadastroTurmasActivity.this, "Preencha o campo nome.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private boolean salvarTurma(Turmas turmas){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("addturmas");
            firebase.child(turmas.getNome()).setValue(turmas);
            Toast.makeText(CadastroTurmasActivity.this, " Cadastro salvo com sucesso!", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
