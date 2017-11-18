package com.example.matheus.projetolistachamada.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matheus.projetolistachamada.Adapter.TurmaAdapter;
import com.example.matheus.projetolistachamada.DAO.ConfiguracaoFirebase;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TurmasActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Turmas> turmas;
    private ArrayAdapter<Turmas> adapterTurmas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerTurmas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turmas);
        turmas = new ArrayList<>();
        adapterTurmas = new TurmaAdapter(this, turmas);
        listView.setAdapter(adapterTurmas);

        firebase = ConfiguracaoFirebase.getFirebase().child("addturmas");



        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("addturmas");
        firebase.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("The " + dataSnapshot.getKey() + " dinosaur's score is " + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        scoresRef.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
//                System.out.println("The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
//            }
//        });

        valueEventListenerTurmas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                turmas.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Turmas turmasNovo = dados.getValue(Turmas.class);
                    turmas.add(turmasNovo);
                }
                adapterTurmas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerTurmas);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerTurmas);
    }
}
