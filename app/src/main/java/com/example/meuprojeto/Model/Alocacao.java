package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Alocacao {
    private String DataInicio;
    private int IdProjeto; //fk de projeto
    private int IdAluno; //fk de aluno

    public Alocacao() {
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String dataInicio) {
        DataInicio = dataInicio;
    }

    public int getIdProjeto() {
        return IdProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        IdProjeto = idProjeto;
    }

    public int getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(int idAluno) {
        IdAluno = idAluno;
    }
}
