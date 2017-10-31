package com.example.matheus.projetolistachamada.Activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.DatabaseReference;

public class CadastroProfessoresActivity extends AppCompatActivity {

    private EditText etNomeProfessor;
    private EditText etMatriculaProfessor;
    private Button btSalvar;

    private Professores professores;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professores);
        etNomeProfessor = (EditText) findViewById(R.id.etNomeProfessor);
        etMatriculaProfessor = (EditText) findViewById(R.id.etMatriculaProfessor);
        btSalvar = (Button) findViewById(R.id.btSalvarCadProfessores);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNomeProfessor.getText().toString().equals("") && !etMatriculaProfessor.getText().toString().equals("")){
                    professores = new Professores();
                    professores.setNome(etNomeProfessor.getText().toString());
                    professores.setMatricula(Integer.valueOf(etMatriculaProfessor.getText().toString()));

                    salvarProfessor(professores);
                    Toast.makeText(CadastroProfessoresActivity.this, "Cadastro salvo com sucesso!",Toast.LENGTH_LONG).show();
                    etNomeProfessor.setText("");
                    etMatriculaProfessor.setText("");
                }else{
                    Toast.makeText(CadastroProfessoresActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private boolean salvarProfessor(Professores professores){
        try{
            firebase = ConfiguracaoFirebase.getFirebase().child("addprofessores");
            firebase.child(professores.getNome()).setValue(professores);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
