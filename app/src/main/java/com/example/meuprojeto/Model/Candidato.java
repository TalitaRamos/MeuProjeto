package com.example.meuprojeto.Model;

/**
 * Created by Talit on 19/01/2018.
 */

public class Candidato {
    private int IdCandidato;
    private String Data;
    private String Situacao;
    private int IdProfessor;//fk de professor
    private int IdAluno;//fk de aluno

    public Candidato() {
    }

    public int getIdCandidato() {
        return IdCandidato;
    }

    public void setIdCandidato(int idCandidato) {
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

    public int getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        IdProfessor = idProfessor;
    }

    public int getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(int idAluno) {
        IdAluno = idAluno;
    }
}
