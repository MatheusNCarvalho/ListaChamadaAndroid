package com.example.matheus.projetolistachamada.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Matheus on 20/10/2017.
 */

public class ConfiguracaoFirebase {
    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth autenticacao;


    public static DatabaseReference getFirebase(){

        if(referenceFirebase == null){
            referenceFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceFirebase;
    }

    public  static  FirebaseAuth getAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }

        return autenticacao;

    }

}
