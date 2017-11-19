package com.example.matheus.projetolistachamada.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.projetolistachamada.Contratos.ProfessorContrato;
import com.example.matheus.projetolistachamada.Entidades.Professores;
import com.example.matheus.projetolistachamada.Helper.SqlLiteConfigHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 19/11/2017.
 */

public class ProfessorDAO {

    private SqlLiteConfigHelp sqlLiteConfig;

    public ProfessorDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }


    public boolean save(List<Professores> professorArrayList) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();
            for (int i = 0; i < professorArrayList.size(); i++) {

                ContentValues valores = new ContentValues();
                valores.put(ProfessorContrato.COLUNA_ID, professorArrayList.get(i).getId());
                valores.put(ProfessorContrato.COLUNA_NOME, professorArrayList.get(i).getNome());
                valores.put(ProfessorContrato.COLUNA_MATRICULA, professorArrayList.get(i).getMatricula());

                linhas = db.insert(ProfessorContrato.NOME_TABELA, null, valores);

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


    public boolean salvar(Professores obj) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();

            ContentValues valores = new ContentValues();
            valores.put(ProfessorContrato.COLUNA_ID, obj.getId());
            valores.put(ProfessorContrato.COLUNA_NOME, obj.getNome());
            valores.put(ProfessorContrato.COLUNA_MATRICULA, obj.getMatricula());

            linhas = db.insert(ProfessorContrato.NOME_TABELA, null, valores);


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

            linhas = db.delete(ProfessorContrato.NOME_TABELA, null, null);
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
//    public boolean update( Professores usuario){
//        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
//        long linhas = 0;
//
//        try{
//            db.beginTransactionNonExclusive();
//            ContentValues valores = new ContentValues();
//            valores.put(ProfessorContrato.COLUNA_NOME, usuario.getNome());
//            valores.put(ProfessorContrato.COLUNA_EMAIL, usuario.getEmail());
//            valores.put(ProfessorContrato.COLUNA_TELEFONE, usuario.getTelefone());
//            valores.put(ProfessorContrato.COLUNA_CPF, usuario.getCpf());
//
//            String condicao = ProfessorContrato.COLUNA_ID+"=?";
//            String[] argumentos = {usuario.getId().toString()};
//
//            linhas = db.update(ProfessorContrato.NOME_TABELA, valores, condicao,argumentos);
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
    public ArrayList<Professores> buscarTodos() {

        SQLiteDatabase db = this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {ProfessorContrato.COLUNA_ID, ProfessorContrato.COLUNA_NOME, ProfessorContrato.COLUNA_MATRICULA};

        Cursor cursor = db.query(false, ProfessorContrato.NOME_TABELA, colunas, null, null, null, null, ProfessorContrato.COLUNA_NOME + " ASC", null);

        ArrayList<Professores> contatos = new ArrayList<Professores>();
        while (cursor.moveToNext()) {
            Professores obj = new Professores();

            obj.setId(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_ID)));
            obj.setNome(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_NOME)));
            obj.setMatricula(cursor.getInt(cursor.getColumnIndex(ProfessorContrato.COLUNA_MATRICULA)));

            contatos.add(obj);
        }

        cursor.close();
        return contatos;

    }
//
//    public ArrayList<Professores> buscarPorNome(String nome){
//        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
//        String[] colunas = {ProfessorContrato.COLUNA_ID, ProfessorContrato.COLUNA_NOME, ProfessorContrato.COLUNA_EMAIL, ProfessorContrato.COLUNA_TELEFONE, ProfessorContrato.COLUNA_CPF};
//
////        mDb.query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
////                        KEY_NAME }, KEY_NAME + " LIKE ?",
////                new String[] {"%"+ filter+ "%" }, null, null, null,
////                null);
//        Cursor cursor = db.query(false,ProfessorContrato.NOME_TABELA,colunas,ProfessorContrato.COLUNA_NOME + " LIKE ?",new String[]{"%"+nome+"%"},null,null,ProfessorContrato.COLUNA_NOME+" ASC",null);
//
//        ArrayList<Professores> contatos = new ArrayList<Professores>();
//        while(cursor.moveToNext()){
//            Professores usuario =  new Professores();
//
//            usuario.setId(cursor.getInt(cursor.getColumnIndex(ProfessorContrato.COLUNA_ID)));
//            usuario.setCpf(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_CPF)));
//            usuario.setNome(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_NOME)));
//            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_TELEFONE)));
//            usuario.setEmail(cursor.getString(cursor.getColumnIndex(ProfessorContrato.COLUNA_EMAIL)));
//            contatos.add(usuario);
//        }
//
//        cursor.close();
//        return contatos;
//
//    }
}
