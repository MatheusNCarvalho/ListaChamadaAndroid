package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.R;

public class Loginctivity extends AppCompatActivity {


    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginctivity);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEmail.getText().toString().isEmpty() && !edtSenha.getText().toString().isEmpty() ){



                }else{
                    Toast.makeText(Loginctivity.this, "Preencha os campos de email e senha", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validarLogin(){

    }

}
