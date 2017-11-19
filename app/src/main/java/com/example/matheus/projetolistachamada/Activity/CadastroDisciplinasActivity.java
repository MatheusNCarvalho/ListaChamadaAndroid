package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

public class CadastroDisciplinasActivity extends AppCompatActivity {

    private EditText etNomeDisciplina;
    private Button btSalvar;

    private Disciplinas disciplinas;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplina);
        etNomeDisciplina = (EditText) findViewById(R.id.etNomeDisciplina);
        btSalvar = (Button) findViewById(R.id.btSalvarCadDisciplina);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNomeDisciplina.getText().toString().equals("")){
                    disciplinas = new Disciplinas();
                    disciplinas.setId(UUID.randomUUID().toString());
                    disciplinas.setNome(etNomeDisciplina.getText().toString());

                    salvarDisciplina(disciplinas);
                    Toast.makeText(CadastroDisciplinasActivity.this, "Cadastro salvo com sucesso!",Toast.LENGTH_LONG).show();
                    etNomeDisciplina.setText("");
                    finish();
                }else{
                    Toast.makeText(CadastroDisciplinasActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private boolean salvarDisciplina(Disciplinas disciplinas){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("adddisciplinas");
            firebase.child(disciplinas.getId()).setValue(disciplinas);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
