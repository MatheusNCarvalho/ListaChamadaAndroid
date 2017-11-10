package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class Loginctivity extends AppCompatActivity {



    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);

        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
            finish();
        }

            edtEmail = (EditText) findViewById(R.id.edtEmail);
            edtSenha = (EditText) findViewById(R.id.edtSenha);
            btnLogar = (Button) findViewById(R.id.btnLogar);

            btnLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!edtEmail.getText().toString().isEmpty() && !edtSenha.getText().toString().isEmpty()) {

                        usuarios = new Usuarios();
                        usuarios.setEmail(edtEmail.getText().toString());
                        usuarios.setSenha(edtSenha.getText().toString());
                        validarLogin();

                    } else {
                        Toast.makeText(Loginctivity.this, "Preencha os campos de email e senha", Toast.LENGTH_LONG).show();
                    }
                }
            });

            textView = (TextView) findViewById(R.id.cliqueCadastro);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Loginctivity.this, CadastroUsuariosActivity.class);
                    startActivity(it);
                }
            });

    }


    private void validarLogin(){
        try{
            autenticacao = ConfiguracaoFirebase.getAutenticacao();
            autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        abrirTelaPrincipal();
                        Toast.makeText(Loginctivity.this, "Login Efetuado com Sucesso", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){
            Log.d("Erro", String.valueOf(e));
        }

    }

    private void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal =  new Intent(Loginctivity.this, CadastrosActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }


    private void pegartokenUsuario(){
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        usuario.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if(task.isSuccessful()){
                    String tokenId = task.getResult().getToken();

                }

            }
        });
    }

}
