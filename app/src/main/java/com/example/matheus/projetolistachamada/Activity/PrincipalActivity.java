package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.matheus.projetolistachamada.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button btCadastros;
    private Button btAlunos;
    private Button btProfessores;

    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private int progressoStatus;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        progressBar = (ProgressBar) findViewById(R.id.progressBar5);
        textView = (TextView ) findViewById(R.id.textView12);

        new Thread(new Runnable() {
            public void run() {
                while (progressoStatus < 100) {
                    progressoStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressoStatus);
                            textView.setText("Fazendo Upload");
                            //textView.setText(progressoStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).start();




        btCadastros = (Button) findViewById(R.id.btCadastros);
        btCadastros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PrincipalActivity.this, AlunosActivity.class);
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
        btProfessores = (Button) findViewById(R.id.btListaProfessores);
        btProfessores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it  = new Intent("Service_Bd");
                startService(it);
//                startActivity(it);

            }
        });

    }
}
