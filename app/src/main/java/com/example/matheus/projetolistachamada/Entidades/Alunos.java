package com.example.matheus.projetolistachamada.Entidades;

import java.util.Date;

/**
 * Created by Brunno on 29/10/17.
 */

public class Alunos {

    private String id;
    private String nome;
    private Date dataMatricula;
    private Integer matricula;
    private String turma;
    private String disciplina;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getTurma() { return turma; }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "Nome: "+ nome + "\n"+ "Matricula: "+ matricula +"\n"+ "Turma: "+ turma +"\n"+ "Disciplina: "+ disciplina;
    }
}