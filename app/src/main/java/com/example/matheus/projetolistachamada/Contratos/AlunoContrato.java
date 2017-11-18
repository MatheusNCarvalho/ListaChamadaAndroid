package com.example.matheus.projetolistachamada.Contratos;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 17/11/2017.
 */

public class AlunoContrato implements BaseColumns {

    private AlunoContrato() {
    }

    public static  final String NOME_TABELA ="alunos";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";
    public static  final String COLUNA_MATRICULA ="matricula";
    public static  final String COLUNA_TURMA ="turma";
}
