package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Alocacao {
    private String DataInicio;
    private String IdProjeto; //fk de projeto
    private String IdAluno; //fk de aluno

    public Alocacao() {
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
}
