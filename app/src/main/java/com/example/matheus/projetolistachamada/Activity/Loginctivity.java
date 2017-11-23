package com.example.matheus.projetolistachamada.Activity;

import android.app.DownloadManager;
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
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Loginctivity extends AppCompatActivity {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    private ArrayList<Usuarios> usuariosArrayList;

    private TextView textView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);
        usuariosArrayList = new ArrayList<>();

        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        if (autenticacao.getCurrentUser() != null) {
            pegarUsuario();
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


    private void validarLogin() {
        try {
            autenticacao = ConfiguracaoFirebase.getAutenticacao();
            autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Loginctivity.this, CarregamentoActivity.class);
                        startActivity(intent);
                        //abrirTelaPrincipal();
                        Toast.makeText(Loginctivity.this, "Login Efetuado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Loginctivity.this, "Email ou senha Incorretos", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Erro", String.valueOf(e));
        }

    }

    private void abrirTelaPrincipal() {
        Intent intentAbrirTelaPrincipal = new Intent(Loginctivity.this, MenuLateralMaterial.class);
        startActivity(intentAbrirTelaPrincipal);
    }


    private void pegarUsuario() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getReference();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String email = usuario.getEmail();

        Query query = firebaseReference.child("usuario").orderByChild("email").startAt(email).endAt(email + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuariosArrayList.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Usuarios usuariosList = dados.getValue(Usuarios.class);
                    imprimir(usuariosList.getNome());
                    usuariosArrayList.add(usuariosList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void imprimir(String nome) {
        Toast.makeText(Loginctivity.this, nome ,Toast.LENGTH_LONG).show();
    }


}
