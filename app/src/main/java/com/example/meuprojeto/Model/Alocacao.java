package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Alocacao {
    private String idAlocacao;
    private String DataInicio;
    private String IdProjeto; //fk de projeto
    private String IdAluno; //fk de aluno
    private String IdProfessor; //fk de professor
    private String IdCandidato;

    public Alocacao() {
    }

    public Alocacao(String idAlocacao, String dataInicio, String idProjeto, String idAluno, String idProfessor,String idCandidato ) {
        this.idAlocacao = idAlocacao;
        this.DataInicio = dataInicio;
        this.IdProjeto = idProjeto;
        this.IdAluno = idAluno;
        this.IdProfessor = idProfessor;
        this.IdCandidato=idCandidato;
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String dataInicio) {
        DataInicio = dataInicio;
    }

    public String getIdProjeto() {
        return IdProjeto;
    }

    public void setIdProjeto(String idProjeto) {
        IdProjeto = idProjeto;
    }

    public String getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(String idAluno) {
        IdAluno = idAluno;
    }

    public String getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        IdProfessor = idProfessor;
    }

    public String getIdAlocacao() {
        return idAlocacao;
    }

    public void setIdAlocacao(String idAlocacao) {
        this.idAlocacao = idAlocacao;
    }

    public String getIdCandidato() {
        return IdCandidato;
    }

    public void setIdCandidato(String idCandidato) {
        IdCandidato = idCandidato;
    }
}
