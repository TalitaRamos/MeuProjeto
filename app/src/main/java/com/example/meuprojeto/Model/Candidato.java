package com.example.meuprojeto.Model;

/**
 * Created by Talit on 19/01/2018.
 */

public class Candidato {
    private String IdCandidato;
    private String Data;
    private String Situacao;
    private String IdProfessor;//fk de professor
    private String IdAluno;//fk de aluno

    public Candidato() {
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

    public String getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        IdProfessor = idProfessor;
    }

    public String getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(String idAluno) {
        IdAluno = idAluno;
    }
}
