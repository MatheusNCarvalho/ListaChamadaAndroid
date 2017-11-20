package com.example.matheus.projetolistachamada.Entidades;

/**
 * Created by Brunno on 29/10/17.
 */

public class Chamadas {

    private String id;
    private String nomeAluno;

    private String turma;
    private String disciplina;
    private String professor;
    private String dataChamada;
    private String faltas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getDataChamada() {
        return dataChamada;
    }

    public void setDataChamada(String dataChamada) {
        this.dataChamada = dataChamada;
    }

    public String getFaltas() {
        return faltas;
    }

    public void setFaltas(String faltas) {
        this.faltas = faltas;
    }
}
