package com.example.meuprojeto.Model;

import com.example.meuprojeto.DAO.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Talit on 18/01/2018.
 */

public class Acesso {
    private String idAcesso;
    private String login;
    private String senha;
    private int tipo;
    //TIPO 1== PROFESSOR
    //TIPO 2==ALUNO

    public Acesso() {
    }

    public String getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(String idAcesso) {
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    //
    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("Acesso").child(String.valueOf(getIdAcesso())).setValue(this);
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("idAcesso",getIdAcesso());
        hashMapUsuario.put("login",getLogin());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("tipo",getTipo());

        return  hashMapUsuario;
    }
}
