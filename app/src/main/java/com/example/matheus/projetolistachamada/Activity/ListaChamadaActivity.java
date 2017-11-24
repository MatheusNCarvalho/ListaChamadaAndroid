package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.Adapter.ChamadaALunoAdapter;
import com.example.matheus.projetolistachamada.DAO.AlunoDAO;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Chamadas;
import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.R;
import com.example.matheus.projetolistachamada.util.VerificaConexaoInternet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ListaChamadaActivity extends AppCompatActivity {

    private ListView listView;
    private EditText etPesquisar;
    private Spinner spDisciplinas;
    private ArrayAdapter<Alunos> adapterAlunoChamada;
    private ArrayList<Alunos> alunos;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseReference;
    private ValueEventListener valueEventListenerAlunos;

    private List<String> arrayDisciplina = new ArrayList<>();
    private ArrayAdapter<String> disciplinaAdapter;

    private Alunos alunosChamada;

    private Chamadas chamadas;
    private Usuarios usuarios;

    private AlunoDAO alunoDAO = new AlunoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chamada);
        alunos = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listChamada);
        registerForContextMenu(listView);

        etPesquisar = (EditText) findViewById(R.id.etPesquisar);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alunosChamada = adapterAlunoChamada.getItem(position);
                return false;
            }
        });

        etPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nome = etPesquisar.getText().toString().trim();
                buscarAluno(nome);
            }
        });

        if (VerificaConexaoInternet.isOnline(this)) {

            disciplinaAdapter = new ArrayAdapter<String>(ListaChamadaActivity.this, android.R.layout.simple_spinner_item, arrayDisciplina);
            // Drop down layout style - list view with radio button
            disciplinaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDisciplinas = (Spinner) findViewById(R.id.spDisci);
            spDisciplinas.setAdapter(disciplinaAdapter);

            firebaseReference = FirebaseDatabase.getInstance().getReference();

            firebaseReference.child("adddisciplinas").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Disciplinas data = snapshot.getValue(Disciplinas.class);
                        arrayDisciplina.add(data.getNome());
                    }
                    disciplinaAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        } else {

            alunos.clear();
            alunos = alunoDAO.buscarTodos();
            adapterAlunoChamada = new ArrayAdapter<Alunos>(ListaChamadaActivity.this, android.R.layout.simple_list_item_1, alunos);

            listView.setAdapter(adapterAlunoChamada);


        }


    }

    private void buscarAluno(String palavra) {

        if (VerificaConexaoInternet.isOnline(this)) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseReference = firebaseDatabase.getReference();
            Query query;
            if (palavra.equals("")) {
                query = firebaseReference.child("addalunos").orderByChild("nome");
            } else {
                query = firebaseReference.child("addalunos").orderByChild("nome").startAt(palavra).endAt(palavra + "\uf8ff");
            }

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    alunos.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        Alunos alunosNovo = dados.getValue(Alunos.class);
                        alunos.add(alunosNovo);
                    }

                    adapterAlunoChamada = new ArrayAdapter<Alunos>(ListaChamadaActivity.this,
                            android.R.layout.simple_list_item_1, alunos);
                    listView.setAdapter(adapterAlunoChamada);
                    adapterAlunoChamada.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {

            alunos.clear();
            alunos = alunoDAO.buscarPorNome(palavra);
            adapterAlunoChamada = new ArrayAdapter<Alunos>(ListaChamadaActivity.this,
                    android.R.layout.simple_list_item_1, alunos);
            listView.setAdapter(adapterAlunoChamada);


        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item01 = menu.add(" 2 faltas");
        item01.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                salvarFaltasChamada("2");

                return false;
            }
        });
        MenuItem item02 = menu.add(" 4 faltas");
        item02.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                salvarFaltasChamada("4");

                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);

    }
    private void carregarUsuario(){

    }

    private boolean salvarFaltasChamada(String faltas) {
        try {
            chamadas = new Chamadas();
            chamadas.setId(UUID.randomUUID().toString());
            chamadas.setNomeAluno(alunosChamada.getNome());
            chamadas.setTurma(alunosChamada.getTurma());

            String data = getDateTime();
            chamadas.setDataChamada(data);
            chamadas.setFaltas(faltas);

            firebaseReference = ConfiguracaoFirebase.getFirebase().child("addchamadas");
            firebaseReference.child(chamadas.getId()).setValue(chamadas);
            Toast.makeText(ListaChamadaActivity.this, "falta salva com sucesso!", Toast.LENGTH_LONG).show();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /* @Override
     protected void onStart() {
         super.onStart();
         if(VerificaConexaoInternet.isOnline(this))
             firebaseReference.addValueEventListener(valueEventListenerAlunos);
     }

     @Override
     protected void onStop() {
         super.onStop();
         if(VerificaConexaoInternet.isOnline(this))
             firebaseReference.removeEventListener(valueEventListenerAlunos);
     }
 */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        buscarAluno("");
    }
}
