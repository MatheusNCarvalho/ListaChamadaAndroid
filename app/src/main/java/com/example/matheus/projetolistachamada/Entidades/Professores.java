package com.example.matheus.projetolistachamada.Entidades;

import java.util.Date;

/**
 * Created by Brunno on 29/10/17.
 */

public class Professores {

    private String id;
    private String nome;
    private Integer matricula;
    private Date dataMatricula;

    private Turmas turmas;

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

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Turmas getTurmas() {
        return turmas;
    }

    public void setTurmas(Turmas turmas) {
        this.turmas = turmas;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +"\n" + "Matricula: "+ matricula;
    }
}
