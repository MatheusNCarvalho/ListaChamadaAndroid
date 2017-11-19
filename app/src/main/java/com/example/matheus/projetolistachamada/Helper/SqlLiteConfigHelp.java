package com.example.matheus.projetolistachamada.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matheus.projetolistachamada.Contratos.AlunoContrato;
import com.example.matheus.projetolistachamada.Contratos.ProfessorContrato;
import com.example.matheus.projetolistachamada.Contratos.TurmaContrato;

import java.util.ArrayList;

/**
 * Created by Matheus on 17/11/2017.
 */

public class SqlLiteConfigHelp extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chamadas.db";

    public SqlLiteConfigHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        for (int i = 0; i < getArraySQLCreate().size(); i++) {
            String sql = getArraySQLCreate().get(i);
            db.execSQL(sql);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public ArrayList<String> getArraySQLCreate() {

//        scriptSQLCreate = new ArrayList<String>();
        ArrayList<String> scriptSQLCreate = new ArrayList<String>();

        scriptSQLCreate.add(" CREATE TABLE IF NOT EXISTS " + TurmaContrato.NOME_TABELA + "(" + TurmaContrato.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TurmaContrato.COLUNA_NOME + " TEXT ); ");

        scriptSQLCreate.add(" CREATE TABLE IF NOT EXISTS " + ProfessorContrato.NOME_TABELA + "(" + ProfessorContrato.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ProfessorContrato.COLUNA_NOME + " TEXT, "
                + ProfessorContrato.COLUNA_MATRICULA + " INTEGER, "
                + ProfessorContrato.COLUNA_DATA_MATRICULA + " DATE ); ");

        scriptSQLCreate.add(" CREATE TABLE IF NOT EXISTS " + AlunoContrato.NOME_TABELA + "(" + AlunoContrato.COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlunoContrato.COLUNA_NOME + " TEXT, "
                + AlunoContrato.COLUNA_MATRICULA + " INTEGER, "
                + AlunoContrato.COLUNA_TURMA + " TEXT ); ");





        return scriptSQLCreate;
    }
}
