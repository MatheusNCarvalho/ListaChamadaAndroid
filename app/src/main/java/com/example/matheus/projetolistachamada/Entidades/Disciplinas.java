package com.example.matheus.projetolistachamada.Entidades;

/**
 * Created by Brunno on 18/11/2017.
 */

public class Disciplinas {
    private String id;
    private String nome;

    public Disciplinas() {
    }

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

    @Override
    public String toString() {
        return nome;
    }
}
