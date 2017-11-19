package com.example.matheus.projetolistachamada.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.matheus.projetolistachamada.Contratos.DisciplinaContrato;

import com.example.matheus.projetolistachamada.Entidades.Disciplinas;
import com.example.matheus.projetolistachamada.Helper.SqlLiteConfigHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 19/11/2017.
 */

public class DisciplinaDAO {

    private SqlLiteConfigHelp sqlLiteConfig;

    public DisciplinaDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }


    public boolean save(List<Disciplinas> disciplinaArrayList) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();
            for (int i = 0; i < disciplinaArrayList.size(); i++) {

                ContentValues valores = new ContentValues();
                valores.put(DisciplinaContrato.COLUNA_ID, disciplinaArrayList.get(i).getId());
                valores.put(DisciplinaContrato.COLUNA_NOME, disciplinaArrayList.get(i).getNome());

                linhas = db.insert(DisciplinaContrato.NOME_TABELA, null, valores);

            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("Error", e.toString());
            db.endTransaction();
        } finally {
            db.endTransaction();
        }


        return linhas == -1 ? false : true;
    }


    public boolean salvar(Disciplinas alunos) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();

            ContentValues valores = new ContentValues();
            valores.put(DisciplinaContrato.COLUNA_ID, alunos.getId());
            valores.put(DisciplinaContrato.COLUNA_NOME, alunos.getNome());

            linhas = db.insert(DisciplinaContrato.NOME_TABELA, null, valores);


            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("Error", e.toString());
            db.endTransaction();
        } finally {
            db.endTransaction();
        }


        return linhas == -1 ? false : true;
    }


    public boolean delete() {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();

        long linhas = 0;

        try {
            db.beginTransactionNonExclusive();

            linhas = db.delete(DisciplinaContrato.NOME_TABELA, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error", e.toString());
            db.endTransaction();
        } finally {
            db.endTransaction();
        }
        return linhas <= 0 ? false : true;

    }

    //
//    public boolean update( Disciplinas usuario){
//        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
//        long linhas = 0;
//
//        try{
//            db.beginTransactionNonExclusive();
//            ContentValues valores = new ContentValues();
//            valores.put(DisciplinaContrato.COLUNA_NOME, usuario.getNome());
//            valores.put(DisciplinaContrato.COLUNA_EMAIL, usuario.getEmail());
//            valores.put(DisciplinaContrato.COLUNA_TELEFONE, usuario.getTelefone());
//            valores.put(DisciplinaContrato.COLUNA_CPF, usuario.getCpf());
//
//            String condicao = DisciplinaContrato.COLUNA_ID+"=?";
//            String[] argumentos = {usuario.getId().toString()};
//
//            linhas = db.update(DisciplinaContrato.NOME_TABELA, valores, condicao,argumentos);
//            db.setTransactionSuccessful();
//
//        }catch (Exception e){
//            Log.e("Error",e.toString());
//            db.endTransaction();
//        }finally {
//            db.endTransaction();
//        }
//
//        return  linhas != -1 ? false : true;
//    }
//
    public ArrayList<Disciplinas> buscarTodos() {

        SQLiteDatabase db = this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {DisciplinaContrato.COLUNA_ID, DisciplinaContrato.COLUNA_NOME};

        Cursor cursor = db.query(false, DisciplinaContrato.NOME_TABELA, colunas, null, null, null, null, DisciplinaContrato.COLUNA_NOME + " ASC", null);

        ArrayList<Disciplinas> contatos = new ArrayList<Disciplinas>();
        while (cursor.moveToNext()) {
            Disciplinas obj = new Disciplinas();

            obj.setId(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_ID)));
            obj.setNome(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_NOME)));

            contatos.add(obj);
        }

        cursor.close();
        return contatos;

    }
//
//    public ArrayList<Disciplinas> buscarPorNome(String nome){
//        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
//        String[] colunas = {DisciplinaContrato.COLUNA_ID, DisciplinaContrato.COLUNA_NOME, DisciplinaContrato.COLUNA_EMAIL, DisciplinaContrato.COLUNA_TELEFONE, DisciplinaContrato.COLUNA_CPF};
//
////        mDb.query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
////                        KEY_NAME }, KEY_NAME + " LIKE ?",
////                new String[] {"%"+ filter+ "%" }, null, null, null,
////                null);
//        Cursor cursor = db.query(false,DisciplinaContrato.NOME_TABELA,colunas,DisciplinaContrato.COLUNA_NOME + " LIKE ?",new String[]{"%"+nome+"%"},null,null,DisciplinaContrato.COLUNA_NOME+" ASC",null);
//
//        ArrayList<Disciplinas> contatos = new ArrayList<Disciplinas>();
//        while(cursor.moveToNext()){
//            Disciplinas usuario =  new Disciplinas();
//
//            usuario.setId(cursor.getInt(cursor.getColumnIndex(DisciplinaContrato.COLUNA_ID)));
//            usuario.setCpf(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_CPF)));
//            usuario.setNome(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_NOME)));
//            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_TELEFONE)));
//            usuario.setEmail(cursor.getString(cursor.getColumnIndex(DisciplinaContrato.COLUNA_EMAIL)));
//            contatos.add(usuario);
//        }
//
//        cursor.close();
//        return contatos;
//
//    }

}
