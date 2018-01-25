package com.example.meuprojeto.Model;

/**
 * Created by Talit on 19/01/2018.
 */

public class Candidato {
    private String IdCandidato;
    private String Data;
    private String Situacao;
    private String IdProjeto;//fk de projeto
    private String IdAluno;//fk de aluno
    private String EmailAluno;

    public Candidato() {
    }

    public Candidato(String idCandidato, String data, String situacao, String idProjeto, String idAluno, String emailAluno) {
        IdCandidato = idCandidato;
        Data = data;
        Situacao = situacao;
        IdProjeto = idProjeto;
        IdAluno = idAluno;
        EmailAluno = emailAluno;
    }

    public String getIdCandidato() {
        return IdCandidato;
    }

    public void setIdCandidato(String idCandidato) {
        IdCandidato = idCandidato;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(String idAluno) {
        IdAluno = idAluno;
    }

    public String getIdProjeto() {
        return IdProjeto;
    }

    public void setIdProjeto(String idProjeto) {
        IdProjeto = idProjeto;
    }

    public String getEmailAluno() {
        return EmailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        EmailAluno = emailAluno;
    }
}
