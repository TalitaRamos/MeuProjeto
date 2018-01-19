package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Professor {
    private int IdProfessor;
    private String NomeProf;
    private String EmailProf;
    private String AreaProf;
    private int IdAcessoProf; //fk de acesso

    //talvez precise se uma variavel tipo ACESSO

    public Professor() {
    }

    public int getIdProfessor() {
        return IdProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        IdProfessor = idProfessor;
    }

    public String getNomeProf() {
        return NomeProf;
    }

    public void setNomeProf(String nomeProf) {
        NomeProf = nomeProf;
    }

    public String getEmailProf() {
        return EmailProf;
    }

    public void setEmailProf(String emailProf) {
        EmailProf = emailProf;
    }

    public String getAreaProf() {
        return AreaProf;
    }

    public void setAreaProf(String areaProf) {
        AreaProf = areaProf;
    }

    public int getIdAcessoProf() {
        return IdAcessoProf;
    }

    public void setIdAcessoProf(int idAcessoProf) {
        IdAcessoProf = idAcessoProf;
    }
}
