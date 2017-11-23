package com.example.matheus.projetolistachamada.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;


public class MenuLateralMaterial extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    private TextView tvNome;

    private ArrayList<Usuarios> usuariosArrayList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral_material);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNome = (TextView) findViewById(R.id.tvMain);
        usuariosArrayList = new ArrayList<>();
        pegarUsuario();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        // trad: se você deseja atualizar os itens em um momento posterior, recomenda-se mantê-lo em uma variável
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home);

        //create the drawer and remember the `Drawer` result object
        final Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header_cadastrado),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cadastro_alunos).withIcon(FontAwesome.Icon.faw_user_plus),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cadastro_disciplinas).withIcon(FontAwesome.Icon.faw_graduation_cap),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cadastro_turmas).withIcon(FontAwesome.Icon.faw_users),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cadastro_chamadas).withIcon(FontAwesome.Icon.faw_users),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_cadastro_usuarios).withIcon(FontAwesome.Icon.faw_user),

                        new SectionDrawerItem().withName(R.string.drawer_item_section_header_listagem),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_listagem_alunos).withIcon(FontAwesome.Icon.faw_search),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_listagem_disciplinas).withIcon(FontAwesome.Icon.faw_search),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_listagem_turmas).withIcon(FontAwesome.Icon.faw_search),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_listagem_usuarios).withIcon(FontAwesome.Icon.faw_search),

                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cogs),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_sign_out)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 2:
                                Intent intentAlunoCadastro = new Intent(MenuLateralMaterial.this, CadastroAlunosActivity.class);
                                startActivity(intentAlunoCadastro);
                                break;
                            case 3:
                                Intent intentProfessorDisciplina = new Intent(MenuLateralMaterial.this, CadastroDisciplinasActivity.class);
                                startActivity(intentProfessorDisciplina);
                                break;
                            case 4:
                                Intent intentTurmaCadastro = new Intent(MenuLateralMaterial.this, CadastroTurmasActivity.class);
                                startActivity(intentTurmaCadastro);
                                break;
                            case 5:
                                Intent intentChamdas = new Intent(MenuLateralMaterial.this, ListaChamadaActivity.class);
                                startActivity(intentChamdas);
                                break;

                            case 6:
                                Intent intentUsuarioCadastro = new Intent(MenuLateralMaterial.this, CadastroUsuariosActivity.class);
                                startActivity(intentUsuarioCadastro);
                                break;

                            case 8:
                                Intent intentAlunoConsulta = new Intent(MenuLateralMaterial.this, AlunosActivity.class);
                                startActivity(intentAlunoConsulta);
                                break;
                            case 9:
                                Intent intentDisciplinaConsulta = new Intent(MenuLateralMaterial.this, DisciplinaActivity.class);
                                startActivity(intentDisciplinaConsulta);
                                break;
                            case 10:
                                Intent intentTurmaConsulta = new Intent(MenuLateralMaterial.this, TurmasActivity.class);
                                startActivity(intentTurmaConsulta);
                                break;
                            case 11:
                                Intent intentUsuarioConsulta = new Intent(MenuLateralMaterial.this, UsuariosActivity.class);
                                startActivity(intentUsuarioConsulta);
                                break;
                            case 14:
                                autenticacao = ConfiguracaoFirebase.getAutenticacao();
                                autenticacao.signOut();
                                Intent intent = new Intent(MenuLateralMaterial.this, Loginctivity.class);
                                startActivity(intent);

                        }


                        return false;
                    }
                })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                    tvNome.setText(" Bem vindo " +usuariosList.getNome());
                    usuariosArrayList.add(usuariosList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
