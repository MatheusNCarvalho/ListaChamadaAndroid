package com.example.matheus.projetolistachamada.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Matheus on 19/11/2017.
 */

public class VerificaConexaoInternet {

    private VerificaConexaoInternet() {
    }

    public  static final boolean isOnline(Context contexto){
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);// Pego a conectividade do contexto

        NetworkInfo netInfo = cm.getActiveNetworkInfo();// Crio o objeto netInfo que recebe as informacoes da Network

        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) { // Se nao tem conectividade retorna false
            return true;
        }
        return false;
    }

}
