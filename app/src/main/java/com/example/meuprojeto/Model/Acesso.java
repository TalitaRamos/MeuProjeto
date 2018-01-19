package com.example.meuprojeto.Model;

/**
 * Created by Talit on 18/01/2018.
 */

public class Acesso {
    private String id;
    private String login;
    private String senha;

    public Acesso() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
