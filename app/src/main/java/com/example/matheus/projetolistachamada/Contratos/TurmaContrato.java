package com.example.matheus.projetolistachamada.Contratos;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 17/11/2017.
 */

public class TurmaContrato implements BaseColumns {

    private TurmaContrato() {
    }

    public static  final String NOME_TABELA ="turma";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";
}
