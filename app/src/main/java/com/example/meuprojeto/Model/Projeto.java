package com.example.meuprojeto.Model;

/**
 * Created by Paloma on 22/01/2018.
 */

public class Projeto {
    private String idProjeto;
    private String nome;
    private String descricao;
    private String status;
    private String IdProfessor;//fk de professor

    public Projeto() {
    }
    public Projeto(String idProjeto, String nome, String  descricao,String status) {
        this.idProjeto = idProjeto;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
    }

    public Projeto(String idProjeto, String nome, String  descricao,String status, String IdProfessor) {
        this.idProjeto = idProjeto;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.IdProfessor=IdProfessor;
    }

    public String getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(String idProjeto) {
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

    public String getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        IdProfessor = idProfessor;
    }
}
