package com.example.matheus.projetolistachamada.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.projetolistachamada.Contratos.TurmaContrato;
import com.example.matheus.projetolistachamada.Entidades.Turmas;
import com.example.matheus.projetolistachamada.Helper.SqlLiteConfigHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 19/11/2017.
 */

public class TurmaDAO {

    private SqlLiteConfigHelp sqlLiteConfig;

    public TurmaDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }


    public boolean save(List<Turmas> turmasArrayList) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();
            for (int i = 0; i < turmasArrayList.size(); i++) {

                ContentValues valores = new ContentValues();
                valores.put(TurmaContrato.COLUNA_NOME, turmasArrayList.get(i).getNome());

                linhas = db.insert(TurmaContrato.NOME_TABELA, null, valores);

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


    public boolean delete() {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();

        long linhas = 0;


        try {
            db.beginTransactionNonExclusive();
//            String condicao = TurmaContrato.COLUNA_ID+"=?";
//            String[] argumentos = {id.toString()};

//            db.delete(TurmaContrato.NOME_TABELA, condicao, argumentos);
            linhas = db.delete(TurmaContrato.NOME_TABELA, null, null);
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
//    public boolean update( Turmas usuario){
//        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
//        long linhas = 0;
//
//        try{
//            db.beginTransactionNonExclusive();
//            ContentValues valores = new ContentValues();
//            valores.put(TurmaContrato.COLUNA_NOME, usuario.getNome());
//            valores.put(TurmaContrato.COLUNA_EMAIL, usuario.getEmail());
//            valores.put(TurmaContrato.COLUNA_TELEFONE, usuario.getTelefone());
//            valores.put(TurmaContrato.COLUNA_CPF, usuario.getCpf());
//
//            String condicao = TurmaContrato.COLUNA_ID+"=?";
//            String[] argumentos = {usuario.getId().toString()};
//
//            linhas = db.update(TurmaContrato.NOME_TABELA, valores, condicao,argumentos);
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
    public ArrayList<String> buscarTodos() {
        SQLiteDatabase db = this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {TurmaContrato.COLUNA_ID, TurmaContrato.COLUNA_NOME};

        Cursor cursor = db.query(false, TurmaContrato.NOME_TABELA, colunas, null, null, null, null, TurmaContrato.COLUNA_NOME + " ASC", null);

        ArrayList<String> contatos = new ArrayList<String>();
        while (cursor.moveToNext()) {
            Turmas turmas = new Turmas();

            turmas.setId(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_ID)));
            turmas.setNome(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_NOME)));
            contatos.add(turmas.toString());
        }

        cursor.close();
        return contatos;

    }
//
//    public ArrayList<Turmas> buscarPorNome(String nome){
//        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
//        String[] colunas = {TurmaContrato.COLUNA_ID, TurmaContrato.COLUNA_NOME, TurmaContrato.COLUNA_EMAIL, TurmaContrato.COLUNA_TELEFONE, TurmaContrato.COLUNA_CPF};
//
////        mDb.query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
////                        KEY_NAME }, KEY_NAME + " LIKE ?",
////                new String[] {"%"+ filter+ "%" }, null, null, null,
////                null);
//        Cursor cursor = db.query(false,TurmaContrato.NOME_TABELA,colunas,TurmaContrato.COLUNA_NOME + " LIKE ?",new String[]{"%"+nome+"%"},null,null,TurmaContrato.COLUNA_NOME+" ASC",null);
//
//        ArrayList<Turmas> contatos = new ArrayList<Turmas>();
//        while(cursor.moveToNext()){
//            Turmas usuario =  new Turmas();
//
//            usuario.setId(cursor.getInt(cursor.getColumnIndex(TurmaContrato.COLUNA_ID)));
//            usuario.setCpf(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_CPF)));
//            usuario.setNome(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_NOME)));
//            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_TELEFONE)));
//            usuario.setEmail(cursor.getString(cursor.getColumnIndex(TurmaContrato.COLUNA_EMAIL)));
//            contatos.add(usuario);
//        }
//
//        cursor.close();
//        return contatos;
//
//    }
}
