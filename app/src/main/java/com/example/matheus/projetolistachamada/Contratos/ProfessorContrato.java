package com.example.matheus.projetolistachamada.Contratos;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 17/11/2017.
 */

public class ProfessorContrato implements BaseColumns {

    private ProfessorContrato() {
    }

    public static  final String NOME_TABELA ="professores";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_MATRICULA ="matricula";
    public static  final String COLUNA_NOME ="nome";
    public static  final String COLUNA_DATA_MATRICULA ="data_matricula";
}
