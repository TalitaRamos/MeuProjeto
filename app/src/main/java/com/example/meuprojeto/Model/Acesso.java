package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Acesso {
    private int idAcesso;
    private String login;
    private String senha;
    private int tipo;
    //TIPO 1== PROFESSOR
    //TIPO 2==ALUNO

    public Acesso() {
    }

    public int getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(int idAcesso) {
        this.idAcesso = idAcesso;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
