package com.example.matheus.projetolistachamada.Contratos;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 19/11/2017.
 */

public class DisciplinaContrato implements BaseColumns {

    private DisciplinaContrato() {
    }

    public static  final String NOME_TABELA ="disciplinas";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";

}
