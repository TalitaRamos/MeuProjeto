package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Aluno {
    private int IdAluno;
    private String NomeAluno;
    private String EmailAluno;
    private String CursoAluno;
    private  int IdAcessoAluno; //fk de acesso
    //talvez precise se uma variavel tipo ACESSO


    public Aluno() {
    }

    public int getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(int idAluno) {
        IdAluno = idAluno;
    }

    public String getNomeAluno() {
        return NomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        NomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return EmailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        EmailAluno = emailAluno;
    }

    public String getCursoAluno() {
        return CursoAluno;
    }

    public void setCursoAluno(String cursoAluno) {
        CursoAluno = cursoAluno;
    }

    public int getIdAcessoAluno() {
        return IdAcessoAluno;
    }

    public void setIdAcessoAluno(int idAcessoAluno) {
        IdAcessoAluno = idAcessoAluno;
    }
}
