package com.example.matheus.projetolistachamada.Contratos;

import android.provider.BaseColumns;

/**
 * Created by Matheus on 20/11/2017.
 */

public class ChamadaContrato implements BaseColumns {

    private ChamadaContrato() {
    }

    public static  final String NOME_TABELA ="chamadas";
    public static  final String COLUNA_ID ="_id";
    public static  final String COLUNA_NOME ="nome";
    public static  final String COLUNA_DISCIPLINA ="disciplina";
    public static  final String COLUNA_TURMA ="turma";
    public static  final String COLUNA_PROFESSOR ="professor";
    public static  final String COLUNA_FALTAS ="faltas";
    public static  final String COLUNA_DATA_CHAMDA ="data_chamada";

}
