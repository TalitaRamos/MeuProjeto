package com.example.meuprojeto.Model;

/**
 * Created by Talit on 17/01/2018.
 */

public class Projeto {
    private int idProjeto;
    private String nome;
    private String descricao;
    private String status;
    private int IdProfessor;//fk de professor

    public Projeto() {
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        IdProfessor = idProfessor;
    }
}
