package com.example.matheus.projetolistachamada.DAO;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matheus.projetolistachamada.Contratos.AlunoContrato;
import com.example.matheus.projetolistachamada.Entidades.Alunos;
import com.example.matheus.projetolistachamada.Entidades.Usuarios;
import com.example.matheus.projetolistachamada.Helper.SqlLiteConfigHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 18/11/2017.
 */

public class AlunoDAO {

    private SqlLiteConfigHelp sqlLiteConfig;

    public AlunoDAO(Context context) {
        this.sqlLiteConfig = new SqlLiteConfigHelp(context);
    }


    public boolean save(List<Alunos> alunosArrayList) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();
            for (int i = 0; i < alunosArrayList.size(); i++) {

                ContentValues valores = new ContentValues();
                valores.put(AlunoContrato.COLUNA_NOME, alunosArrayList.get(i).getNome());
                valores.put(AlunoContrato.COLUNA_MATRICULA, alunosArrayList.get(i).getMatricula());
                valores.put(AlunoContrato.COLUNA_TURMA, alunosArrayList.get(i).getTurma());

                linhas = db.insert(AlunoContrato.NOME_TABELA, null, valores);

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



    public boolean salvar(Alunos alunos) {
        SQLiteDatabase db = this.sqlLiteConfig.getWritableDatabase();
        long linhas = 0;

        try {

            db.beginTransactionNonExclusive();

                ContentValues valores = new ContentValues();
                valores.put(AlunoContrato.COLUNA_ID, alunos.getId());
                valores.put(AlunoContrato.COLUNA_NOME, alunos.getNome());
                valores.put(AlunoContrato.COLUNA_MATRICULA, alunos.getMatricula());
                valores.put(AlunoContrato.COLUNA_TURMA, alunos.getTurma());

                linhas = db.insert(AlunoContrato.NOME_TABELA, null, valores);


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
//            String condicao = AlunoContrato.COLUNA_ID+"=?";
//            String[] argumentos = {id.toString()};

//            db.delete(AlunoContrato.NOME_TABELA, condicao, argumentos);
            linhas = db.delete(AlunoContrato.NOME_TABELA, null, null);
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
//    public boolean update( Alunos usuario){
//        SQLiteDatabase db =  this.sqlLiteConfig.getWritableDatabase();
//        long linhas = 0;
//
//        try{
//            db.beginTransactionNonExclusive();
//            ContentValues valores = new ContentValues();
//            valores.put(AlunoContrato.COLUNA_NOME, usuario.getNome());
//            valores.put(AlunoContrato.COLUNA_EMAIL, usuario.getEmail());
//            valores.put(AlunoContrato.COLUNA_TELEFONE, usuario.getTelefone());
//            valores.put(AlunoContrato.COLUNA_CPF, usuario.getCpf());
//
//            String condicao = AlunoContrato.COLUNA_ID+"=?";
//            String[] argumentos = {usuario.getId().toString()};
//
//            linhas = db.update(AlunoContrato.NOME_TABELA, valores, condicao,argumentos);
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
    public ArrayList<Alunos> buscarTodos() {
        SQLiteDatabase db = this.sqlLiteConfig.getReadableDatabase();
        String[] colunas = {AlunoContrato.COLUNA_ID, AlunoContrato.COLUNA_NOME, AlunoContrato.COLUNA_MATRICULA, AlunoContrato.COLUNA_TURMA};

        Cursor cursor = db.query(false, AlunoContrato.NOME_TABELA, colunas, null, null, null, null, AlunoContrato.COLUNA_NOME + " ASC", null);

        ArrayList<Alunos> contatos = new ArrayList<Alunos>();
        while (cursor.moveToNext()) {
            Alunos usuario = new Alunos();

            usuario.setId(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_NOME)));
            usuario.setMatricula(cursor.getInt(cursor.getColumnIndex(AlunoContrato.COLUNA_MATRICULA)));
            usuario.setTurma(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_TURMA)));
            contatos.add(usuario);
        }

        cursor.close();
        return contatos;

    }
//
//    public ArrayList<Alunos> buscarPorNome(String nome){
//        SQLiteDatabase db =  this.sqlLiteConfig.getReadableDatabase();
//        String[] colunas = {AlunoContrato.COLUNA_ID, AlunoContrato.COLUNA_NOME, AlunoContrato.COLUNA_EMAIL, AlunoContrato.COLUNA_TELEFONE, AlunoContrato.COLUNA_CPF};
//
////        mDb.query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
////                        KEY_NAME }, KEY_NAME + " LIKE ?",
////                new String[] {"%"+ filter+ "%" }, null, null, null,
////                null);
//        Cursor cursor = db.query(false,AlunoContrato.NOME_TABELA,colunas,AlunoContrato.COLUNA_NOME + " LIKE ?",new String[]{"%"+nome+"%"},null,null,AlunoContrato.COLUNA_NOME+" ASC",null);
//
//        ArrayList<Alunos> contatos = new ArrayList<Alunos>();
//        while(cursor.moveToNext()){
//            Alunos usuario =  new Alunos();
//
//            usuario.setId(cursor.getInt(cursor.getColumnIndex(AlunoContrato.COLUNA_ID)));
//            usuario.setCpf(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_CPF)));
//            usuario.setNome(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_NOME)));
//            usuario.setTelefone(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_TELEFONE)));
//            usuario.setEmail(cursor.getString(cursor.getColumnIndex(AlunoContrato.COLUNA_EMAIL)));
//            contatos.add(usuario);
//        }
//
//        cursor.close();
//        return contatos;
//
//    }
}
