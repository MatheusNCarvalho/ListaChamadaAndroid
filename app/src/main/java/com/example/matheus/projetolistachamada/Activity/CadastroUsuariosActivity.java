package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.Helper.Base64Custom;
import com.example.matheus.projetolistachamada.Helper.Preferencias;
import com.example.matheus.projetolistachamada.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class CadastroUsuariosActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfirmaSenha;
    private Button btSalvar;

    private Usuarios usuarios;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        etNome = (EditText) findViewById(R.id.etNomeUsuario);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etConfirmaSenha = (EditText) findViewById(R.id.etConfirmaSenha);

        btSalvar = (Button) findViewById(R.id.btSalvarCadUsuario);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNome.getText().toString().equals("")&&
                        etEmail.getText().toString().equals("") &&
                            etSenha.getText().toString().equals("") &&
                                etConfirmaSenha.getText().toString().equals("")){
                    if(etSenha.getText().toString().equals(etConfirmaSenha.getText().toString())){
                        usuarios = new Usuarios();
                        usuarios.setNome(etNome.getText().toString());
                        usuarios.setEmail(etEmail.getText().toString());
                        usuarios.setSenha(etSenha.getText().toString());

                        salvarUsuario();

                        etNome.setText("");
                        etEmail.setText("");
                        etSenha.setText("");
                        etConfirmaSenha.setText("");
                    }
                    else{
                        Toast.makeText(CadastroUsuariosActivity.this, "As senhas não conferem", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(CadastroUsuariosActivity.this, "Preencha os campos corretamente!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void salvarUsuario(){
       autenticacao = ConfiguracaoFirebase.getAutenticacao();
       autenticacao.createUserWithEmailAndPassword(
               usuarios.getEmail(),
               usuarios.getSenha())
       .addOnCompleteListener(CadastroUsuariosActivity.this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferenciasAndroid = new Preferencias(CadastroUsuariosActivity.this);
                    preferenciasAndroid.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    Toast.makeText(CadastroUsuariosActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    abrirLoginUsuario();
                }else{
                    String erroExcecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Senha inválida! A senha deve conter no mínimo 8 caracteres.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "E-mail inválido! Digite um novo e-mail.";
                    }catch (FirebaseAuthUserCollisionException e ){
                        erroExcecao = "E-mail já cadastrado no sistema.";
                    }catch (Exception e){
                        erroExcecao = "Falha ao efetuar o cadastro.";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuariosActivity.this, "Erro: "+ erroExcecao, Toast.LENGTH_SHORT).show();
                }
           }

       });
    }
    public void abrirLoginUsuario(){
        Intent it = new Intent(CadastroUsuariosActivity.this, Loginctivity.class);
        startActivity(it);
        finish();
    }
}
